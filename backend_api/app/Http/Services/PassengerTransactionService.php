<?php

namespace App\Http\Services;

use Exception;
use App\Models\GoodsTransaction;
use App\Models\PassengerRideBooking;
use App\Models\PassengerTransaction;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;
use App\Http\Repositories\PassengerTransactionRepository;
use App\Models\PaymentMethod;

class PassengerTransactionService
{
    protected $transactionRepository;

    public function __construct(PassengerTransactionRepository $repo)
    {
        $this->transactionRepository = $repo;
    }

    // List semua transaksi
    public function listTransactions()
    {
        return $this->transactionRepository->getAll();
    }

    // Detail transaksi
    public function getTransaction($id)
    {
        return $this->transactionRepository->findById($id);
    }

    // Transaksi per customer
    public function listByCustomer($customerId)
    {
        return $this->transactionRepository->getByCustomer($customerId);
    }

    // Transaksi per booking
    public function getByBooking($bookingId)
    {
        return $this->transactionRepository->getByBooking($bookingId);
    }

    public function createTransaction(array $data)
    {
        $validator = Validator::make($data, [
            'passenger_ride_booking_id' => 'required|exists:passenger_ride_bookings,id',
            'customer_id' => 'required|exists:customers,id',
            'total_amount' => 'required|integer|min:0',
            'payment_method_id' => 'required|exists:payment_methods,id',
            'payment_proof_img' => 'nullable|string',
            'payment_status' => 'nullable|string|in:Pending,Diterima,Ditolak,Credited',
            'credit_used' => 'nullable|integer|min:0',
            'transaction_date' => 'nullable|date',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        $booking = PassengerRideBooking::find($data['passenger_ride_booking_id']);
        if(!$booking){
            throw ValidationException::withMessages([
                'passenger_ride_booking_id' => 'Booking not found',
            ]);
        }

        // Generate transaction code
        $transaction_code = $this->generateCode($booking->booking_code);
        $data['transaction_code'] = $transaction_code;
        $data['payment_status'] = $data['payment_status'] ?? 'Pending';
        $data['transaction_date'] = $data['transaction_date'] ?? now();

        // 1️⃣ Simpan transaksi dulu (status masih Pending)
        $transaction = $this->transactionRepository->create($data);

        // ambil payment method
        $paymentMethod = PaymentMethod::find($data['payment_method_id']);

        // 2️⃣ Siapkan payload Midtrans /charge
        $payload = [
            "transaction_details" => [
                "order_id"      => $transaction->transaction_code,
                "gross_amount"  => $transaction->total_amount,
            ],
            "customer_details" => [
                "first_name" => $transaction->customer->full_name,
                "email"      => $transaction->customer->user->email ?? "email@example.com",
                "phone"      => $transaction->customer->telephone,
            ],

        ];

        switch ($paymentMethod->code) {

    // VA — BNI, BRI, BCA, Permata
    case 'bni':
    case 'bri':
    case 'bca':
    case 'permata':
        $payload['payment_type'] = 'bank_transfer';
        $payload['bank_transfer'] = [
            'bank' => $paymentMethod->code
        ];
        break;

    // QRIS
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


        // 3️⃣ Kirim ke Midtrans
        $serverKey = config('midtrans.server_key');
        $apiUrl = config('midtrans.api_url') . 'charge';

        $response = Http::withBasicAuth($serverKey, "")
            ->withHeaders(["Content-Type" => "application/json"])
            ->post($apiUrl, $payload);

        if (!$response->successful()){
            $transaction->update(['payment_response_raw' => $response->body()]);
            throw new Exception("Failed to create midtrans transaction");
        }

        $responseJson = $response->json();

        $transaction->update([
            'payment_response_raw' => $response->body()
        ]);


        // 4️⃣ Ambil data dari respon Midtrans
        $vaNumber = null;
    $deeplink = null;
    $qrString = null;

    $paymentType = $responseJson['payment_type'] ?? null;
    $midtransTransactionId = $responseJson['transaction_id'] ?? null;
    $orderId = $responseJson['order_id'] ?? null;
    $expiredAt = $responseJson['expiry'] ?? null;

    // =============================
    // VA Number
    // =============================
    if (!empty($responseJson['va_numbers'])) {
        $vaNumber = $responseJson['va_numbers'][0]['va_number'] ?? null;
    } elseif (!empty($responseJson['permata_va_number'])) {
        $vaNumber = $responseJson['permata_va_number'];
    }

    // =============================
    // Actions Handler
    // =============================
    if (!empty($responseJson['actions'])) {
        foreach ($responseJson['actions'] as $act) {

        $name = strtolower($act['name'] ?? '');
        $url = $act['url'] ?? null;

        if (!$url) continue;

        // =============================
        // 1️⃣ Ambil Deeplink E-wallet
        // =============================
        if (
            str_contains($name, 'deeplink') ||      // deeplink-redirect
            str_contains($name, 'app')              // gopay-app-redirect (jika muncul)
        ) {
            // Ambil hanya sekali — jangan ditimpa
            if (!$deeplink) {
                $deeplink = $url;
            }
        }


        // =============================
        // 2️⃣ Ambil QR Code URL
        // =============================
        if (str_contains($name, 'qr')) {
            $qrString = $url;
        }
    }
}
// dd($deeplink);

        // 5️⃣ Update ke DB
        $transaction->update([
            'midtrans_order_id'       => $orderId,
            'midtrans_transaction_id' => $midtransTransactionId,
            'payment_type'            => $paymentType,
            'va_number'               => $vaNumber,
            'ewallet_deeplink'        => $deeplink,
            'qr_string'               => $qrString,
            'payment_expired_at'      => $expiredAt,
            'payment_response_raw'    => json_encode($responseJson),
            'payment_method_id'       => $paymentMethod->id,
        ]);

        // 6️⃣ Return sama seperti sebelumnya (ada VA & raw_response)
        return $transaction->fresh([
            'customer',
            'passengerRideBooking',
            'paymentMethod'
        ]);
    }

    public function generateCode($bookingCode){
        $cleanCode = str_replace([' ','_'], '-', strtoupper($bookingCode));
        $today = now()->format('Ymd');
        $countToday = PassengerTransaction::whereDate('created_at', now()->toDateString())->count()+1;

        return 'TX-'. $cleanCode . '-' . str_pad($countToday, 4, '0', STR_PAD_LEFT);
    }

    // Update transaksi (misal update bukti pembayaran)
    public function updateTransaction($id, array $data)
    {
        $validator = Validator::make($data, [
            'total_amount' => 'sometimes|integer|min:0',
            'payment_proof_img' => 'nullable|string',
            'payment_status' => 'sometimes|string|in:Pending,Diterima,Ditolak,Credited',
            'credit_used' => 'sometimes|integer|min:0',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->transactionRepository->update($id, $data);
    }

    // Hapus transaksi
    public function deleteTransaction($id)
    {
        return $this->transactionRepository->delete($id);
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

        return $this->transactionRepository->update($id, [
            'payment_status' => $status,
        ]);
    }
}
