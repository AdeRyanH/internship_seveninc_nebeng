<?php

namespace App\Http\Services;

use Exception;
use App\Models\PaymentMethod;
use App\Models\GoodsRideBooking;
use App\Models\GoodsTransaction;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;
use App\Http\Repositories\GoodsTransactionRepository;

class GoodsTransactionService
{
    protected $repository;

    public function __construct(GoodsTransactionRepository $repo)
    {
        $this->repository = $repo;
    }

    public function listTransactions()
    {
        return $this->repository->getAll();
    }

    public function getByBooking($bookingId)
    {
        return $this->repository->getByBooking($bookingId);
    }

    public function getTransaction($id)
    {
        return $this->repository->findById($id);
    }

    public function listByCustomer($customerId)
    {
        return $this->repository->getByCustomer($customerId);
    }

    public function listByStatus($status)
    {
        return $this->repository->getByStatus($status);
    }


    public function createTransaction(array $data)
    {
        // Validasi input
        $validator = Validator::make($data, [
            'goods_ride_booking_id' => 'required|exists:goods_ride_bookings,id',
            'customer_id'           => 'required|exists:customers,id',
            'total_amount'          => 'required|numeric|min:0',
            'payment_method_id'     => 'required|exists:payment_methods,id',
            'payment_proof_img'     => 'nullable|string|max:255',
            'payment_status'        => 'nullable|string|in:Pending,Diterima,Ditolak,Credited',
            'credit_used'           => 'nullable|integer|min:0',
            'transaction_date'      => 'nullable|date',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        $booking = GoodsRideBooking::find($data['goods_ride_booking_id']);

        if (!$booking) {
            throw ValidationException::withMessages([
                'goods_ride_booking_id' => 'Booking not found'
            ]);
        }

        $transaction_code = $this->generateCode($booking->booking_code);

        // Set default values
        $data['transaction_code'] = $transaction_code;
        $data['payment_status'] = $data['payment_status'] ?? 'Pending';
        $data['transaction_date'] = $data['transaction_date'] ?? now();

        // Simpan transaksi ke DB
        $transaction = $this->repository->create($data);

        $paymentMethod = PaymentMethod::find($data['payment_method_id']);

        // Payload Midtrans
        $payload = [
            "transaction_details" => [
                "order_id"     => $transaction->transaction_code,
                "gross_amount" => $transaction->total_amount,
            ],
            "customer_details" => [
                "first_name" => $transaction->customer->full_name,
                "email"      => $transaction->customer->user->email ?? "email@example.com",
                "phone"      => $transaction->customer->telephone,
            ],
        ];

        // Tentukan jenis pembayaran
        switch ($paymentMethod->code) {
            case 'bni':
            case 'bri':
            case 'bca':
            case 'permata':
                $payload['payment_type'] = 'bank_transfer';
                $payload['bank_transfer'] = ['bank' => $paymentMethod->code];
                break;

            case 'qris':
                $payload['payment_type'] = 'qris';
                break;

            case 'gopay':
                $payload['payment_type'] = 'gopay';
                $payload['gopay'] = [
                    'enable_callback' => true,
                    'callback_url' => rtrim(config('app.url'), '/') . '/payment/midtrans/callback'
                ];
                break;

            case 'shopeepay':
                $payload['payment_type'] = 'shopeepay';
                $payload['shopeepay'] = [
                    'callback_url' => rtrim(config('app.url'), '/') . '/payment/midtrans/callback'
                ];
                break;

            case 'ovo':
                $payload['payment_type'] = 'ovo';
                $payload['ovo'] = [
                    'phone_number' => $transaction->customer->telephone
                ];
                break;

            default:
                throw new Exception("Unsupported payment method code: {$paymentMethod->code}");
        }

        // Kirim request ke Midtrans
        $serverKey = config('midtrans.server_key');
        $apiUrl = config('midtrans.api_url') . 'charge';

        $response = Http::withBasicAuth($serverKey, "")
            ->withHeaders(["Content-Type" => "application/json"])
            ->post($apiUrl, $payload);

        if (!$response->successful()) {
            $transaction->update(['payment_response_raw' => $response->body()]);
            throw new Exception("Failed to create Midtrans transaction");
        }

        $responseJson = $response->json();
        $rawMidtrans = $response->body();

        // Ambil data dari response Midtrans
        $vaNumber = null;
        $deeplink = $responseJson['actions'] ? null : $responseJson['deeplink'] ?? null;
        $qrString = $responseJson['qr_string'] ?? null;

        $paymentType = $responseJson['payment_type'] ?? null;
        $midtransTransactionId = $responseJson['transaction_id'] ?? null;
        $orderId = $responseJson['order_id'] ?? null;
        $expiredAt = $responseJson['expiry_time'] ?? null;

        // Ambil VA number
        if (!empty($responseJson['va_numbers'])) {
            $vaNumber = $responseJson['va_numbers'][0]['va_number'] ?? null;
        } elseif (!empty($responseJson['permata_va_number'])) {
            $vaNumber = $responseJson['permata_va_number'];
        }

        // Ambil QR dan Deeplink dari actions jika ada
        if (!empty($responseJson['actions'])) {
            foreach ($responseJson['actions'] as $act) {
                if (isset($act['name'])) {
                    if (str_contains($act['name'], 'qr')) {
                        $qrString = $qrString ?? $act['url']; // fallback hanya kalau belum ada
                    }
                    if (str_contains($act['name'], 'deeplink')) {
                        $deeplink = $deeplink ?? $act['url']; // fallback hanya kalau belum ada
                    }
                }
            }
        }

        // Update ke DB
        $transaction->update([
            'midtrans_order_id'       => $orderId,
            'midtrans_transaction_id' => $midtransTransactionId,
            'payment_type'            => $paymentType,
            'va_number'               => $vaNumber,
            'ewallet_deeplink'        => $deeplink,
            'qr_string'               => $qrString,
            'payment_expired_at'      => $expiredAt,
            'payment_response_raw'    => $rawMidtrans,
            'payment_method_id'       => $paymentMethod->id,
        ]);

        return $transaction->fresh([
            'customer',
            'goodsRideBooking',
            'paymentMethod'
        ]);
    }



    public function generateCode($bookingCode){
        $cleanCode = str_replace([' ','_'], '-', strtoupper($bookingCode));
        $today = now()->format('Ymd');
        $countToday = GoodsTransaction::whereDate('created_at', now()->toDateString())->count()+1;

        return 'TX-'. $cleanCode . '-' . str_pad($countToday, 4, '0', STR_PAD_LEFT);
    }

    public function updateTransaction($id, array $data)
    {
        $validator = Validator::make($data, [
            'total_amount' => 'sometimes|integer|min:0',
            'payment_status'   => 'sometimes|string|in:Pending,Diterima,Ditolak,Credited',
            'payment_proof_img'=> 'nullable|string|max:255',
            'credit_used'      => 'nullable|integer|min:0',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->repository->update($id, $data);
    }

    public function deleteTransaction($id)
    {
        return $this->repository->delete($id);
    }

    // Ubah status pembayaran
    public function updateStatus($id, string $status)
    {
        $validStatuses = ['Pending', 'Diterima', 'Ditolak', 'Credited'];
        if (!in_array($status, $validStatuses)) {
            throw ValidationException::withMessages([
                'status' => 'Invalid payment status value.',
            ]);
        }

        return $this->repository->update($id, [
            'payment_status' => $status,
        ]);
    }
}
