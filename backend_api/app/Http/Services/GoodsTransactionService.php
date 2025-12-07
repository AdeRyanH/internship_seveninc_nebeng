<?php

namespace App\Http\Services;

use App\Http\Repositories\GoodsTransactionRepository;
use App\Models\GoodsRideBooking;
use App\Models\GoodsTransaction;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;

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
        $validator = Validator::make($data, [
            'goods_ride_booking_id' => 'required|exists:goods_ride_bookings,id',
            'customer_id'           => 'required|exists:customers,id',
            'total_amount'          => 'required|numeric|min:0',
            'payment_method_id'     => 'required|exists:payment_methods,id',
            'payment_proof_img'     => 'nullable|string|max:255',
            'payment_status'        => 'required|string|in:pending,diterima,ditolak,credited',
            'credit_used'           => 'nullable|integer|min:0',
            'transaction_date'      => 'nullable|date',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        $booking = GoodsRideBooking::find($data['goods_ride_booking_id']);

        if(!$booking){
            throw ValidationException::withMessages([
                'goods_ride_booking_id' => 'booking not found'
            ]);
        }

        $transaction_code = $this->generateCode($booking->booking_code);

        // Default status
        $data['transaction_code'] = $transaction_code;
        $data['payment_status'] = $data['payment_status'] ?? 'pending';
        $data['transaction_date'] = $data['transaction_date'] ?? now();

        return $this->repository->create($data);
    }

    public function generateCode($bookingCode){
        $cleanCode = str_replace(['','_'], '-', strtoupper($bookingCode));
        $today = now()->format('Ymd');
        $countToday = GoodsTransaction::whereDate('created_at', now()->toDateString())->count()+1;

        return 'TX-'. $cleanCode . '-' . str_pad($countToday, 4, '0', STR_PAD_LEFT);
    }

    public function updateTransaction($id, array $data)
    {
        $validator = Validator::make($data, [
            'payment_status'   => 'sometimes|string|in:pending,diterima,ditolak,credited',
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
}
