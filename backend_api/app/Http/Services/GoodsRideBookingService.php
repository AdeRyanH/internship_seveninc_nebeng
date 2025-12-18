<?php

namespace App\Http\Services;

use Carbon\Carbon;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;
use App\Http\Repositories\GoodsRideBookingRepository;

class GoodsRideBookingService
{
    protected $goodsRideBookingRepository;

    public function __construct(GoodsRideBookingRepository $repo)
    {
        $this->goodsRideBookingRepository = $repo;
    }

    protected function generateBookingNumbers($prefix = 'G'){
        $date = Carbon :: now()->format('Ymd');
        $countToday = $this->goodsRideBookingRepository->countByDate(Carbon::today());

        $sequence = str_pad($countToday + 1, 4, '0', STR_PAD_LEFT);
        return "{$prefix}-{$date}-{$sequence}";
    }

    // List semua booking
    public function listBookings($filters = [])
    {
        return $this->goodsRideBookingRepository->getAll($filters);
    }

    // Get by ID
    public function getBooking($id)
    {
        return $this->goodsRideBookingRepository->findById($id);
    }

    // Get By Code
    public function getByCode(string $code) {
        return $this->goodsRideBookingRepository->getByCode($code);
    }

    // Get by Driver ID
    public function listByDriver($driverId)
    {
        return $this->goodsRideBookingRepository->getByDriver($driverId);
    }

    // Filter by Status
    public function listByStatus($status)
    {
        return $this->goodsRideBookingRepository->getByStatus($status);
    }

    // Create new booking
    public function createBooking(array $data)
    {
        $validator = Validator::make($data, [
            'goods_ride_id' => 'required|exists:goods_rides,id',
            'customer_id' => 'required|exists:customers,id',
            'item_weight' => 'required|numeric|min:1',
            'item_description' => 'nullable|string|max:500',
            'item_img' => 'nullable|string|max:255',
            'total_price' => 'required|numeric|min:0',
            'status' => 'required|string|in:pending,diterima,ditolak',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        $data['status'] = $data['status'] ?? 'pending';
        $data['booking_code'] = $this->generateBookingNumbers('G');

        return $this->goodsRideBookingRepository->create($data);
    }

    // Update booking (misalnya ubah status)
    public function updateBooking($id, array $data)
    {
        $validator = Validator::make($data, [
            'item_weight' => 'sometimes|numeric|min:1',
            'item_description' => 'nullable|string|max:500',
            'item_img' => 'nullable|string|max:255',
            'total_price' => 'sometimes|numeric|min:0',
            'status' => 'sometimes|required|string|in:pending,diterima,ditolak',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->goodsRideBookingRepository->update($id, $data);
    }

    // Hapus booking
    public function deleteBooking($id)
    {
        return $this->goodsRideBookingRepository->delete($id);
    }

    public function paginateBookings($perPage = 10, $filters = []){
        return $this->goodsRideBookingRepository->paginate($perPage, $filters);
    }
}
