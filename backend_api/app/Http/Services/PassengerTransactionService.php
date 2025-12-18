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
            'customer_id'               => 'required|exists:customers,id',
            'total_amount'              => 'required|integer|min:0',
            'payment_method_id'         => 'required|exists:payment_methods,id',
            'payment_proof_img'         => 'nullable|string',
            'payment_status'            => 'nullable|string|in:pending,diterima,ditolak,credited',
            'credit_used'               => 'nullable|integer|min:0',
            'transaction_date'          => 'nullable|date',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        $booking = PassengerRideBooking::find($data['passenger_ride_booking_id']);
        // if(!$booking){
        //     throw ValidationException::withMessages([
        //         'passenger_ride_booking_id' => 'Booking not found',
        //     ]);
        // }
        logger('BEFORE CREATE TRANSACTION', [
            'booking_id' => $booking->id,
            'status' => $booking->status
        ]);

        if ($booking->status !== "pending") {
            throw ValidationException::withMessages([
                'booking' => 'Booking already processed',
            ]);
        }

        $method     = PaymentMethod::findOrFail($data['payment_method_id']);

        $channel    = strtoupper($method->bank_name);   // CASH, BCA, BRI, QRIS, DANA
        $isMidtrans = $method->account_number === "MIDTRANS_NO_NUMBER";

        // Generate transaction code
        // $transaction_code = $this->generateCode($booking->booking_code);
        $data['transaction_code'] = $this->generateCode($booking->booking_code ?? 'BOOKING');
        // $data['payment_status'] = $data['payment_status'] ?? 'Pending';
        $data['payment_status'] = 'pending';
        // $data['transaction_date'] = $data['transaction_date'] ?? now();
        $data['transaction_date'] = now();

        // 1️⃣ CREATE PASSENGER TRANSACTION
        $transaction = $this->transactionRepository->create($data);

        // =====================================================
        // ✅ CASE 1: CASH (STOP HERE)
        // =====================================================
        if ($channel === 'CASH') {
            return $transaction->fresh(with: [
                'customer',
                'passengerRideBooking',
                'paymentMethod'
            ]);
        }

        // =====================================================
        // ✅ CASE 2: MIDTRANS
        // =====================================================
        if (!$isMidtrans) {
            throw new Exception('Invalid payment method configuration');
        }

        // 2️⃣ Siapkan payload Midtrans /charge
        $payload = [
            // "payment_type" => "bank_transfer",
            "transaction_details" => [
                "order_id"      => $transaction->transaction_code,
                "gross_amount"  => $transaction->total_amount,
            ],
            "customer_details" => [
                "first_name" => $transaction->customer->full_name,
                "email"      => $transaction->customer->user->email ?? "email@example.com",
                "phone"      => $transaction->customer->telephone,
            ]
            // "bank_transfer" => [
            //     "bank" => "bni"  // sementara fix dulu, nanti jika mau dynamic bisa berdasarkan payment_method_id
            // ]
        ];

        switch ($channel) {

            case 'BCA':
                $payload['payment_type']    = 'bank_transfer';
                $payload['bank_transfer']   = ['bank' => 'bca'];
                break;

            case 'BRI':
                $payload['payment_type']    = 'bank_transfer';
                $payload['bank_transfer']   = ['bank' => 'bri'];
                break;

            case 'QRIS':
                $payload['payment_type']    = 'qris';
                break;

            case 'DANA':
                $payload['payment_type']    = 'ewallet';
                $payload['ewallet']         = ['type' => 'dana'];
                break;

            default:
                throw new Exception("Unsupported payment channel: {$channel}");
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

        $rawMidtrans = $response->body();


        // 4️⃣ Ambil data dari respon Midtrans
        // $vaNumber = $response['va_numbers'][0]['va_number'] ?? null;
        // $paymentType = $response['payment_type'] ?? null;
        // $midtransTransactionId = $response['transaction_id'] ?? null;
        // $orderId = $response['order_id'] ?? null;
        // $expiredAt = $response['expiry_time'] ?? null;

        // 5️⃣ PARSE RESPONSE (SAFE)
        $transaction->update([
            'midtrans_order_id'       => $response['order_id']          ??  null,
            'midtrans_transaction_id' => $response['transaction_id']    ?? null,
            'payment_type'            => $response['payment_type']      ?? null,
            'va_number'               => $response['va_numbers'][0]['va_number']  ?? null,
            'payment_expired_at'      => $response['expiry_time']       ??null,
            'payment_response_raw'    => $response,
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
            'total_amount'      => 'sometimes|integer|min:0',
            'payment_proof_img' => 'nullable|string',
            'payment_status'    => 'sometimes|string|in:pending,diterima,ditolak,credited',
            'credit_used'       => 'sometimes|integer|min:0',
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
        $validStatuses = ['pending', 'diterima', 'ditolak', 'credited'];
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
