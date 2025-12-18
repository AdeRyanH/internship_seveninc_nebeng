<?php

namespace App\Http\Services;

// use Dotenv\Exception\ValidationException;
use Illuminate\Support\Facades\Validator;
use App\Http\Repositories\GoodsRideRepository;
use Illuminate\Validation\ValidationException;

class GoodsRideService {
    protected $goodsRideRepository;

    public function __construct(GoodsRideRepository $goodsRideRepository) {
        $this->goodsRideRepository = $goodsRideRepository;
    }

    public function listGoodsRides() {
        return $this->goodsRideRepository->getAll();
    }

    public function getGoodsRide($id) {
        return $this->goodsRideRepository->findById($id);
    }

    public function listByDriver($driverId)
    {
        return $this->goodsRideRepository->getByDriver($driverId);
    }

    public function createGoodsRide(array $data) {
        $validator = Validator::make($data, [
            'driver_id'             => 'required|exists:drivers,id',
            'transport_type'        => 'required|string|max:100',
            'departure_terminal_id' => 'required|exists:terminals,id',
            'arrival_terminal_id'   => 'required|exists:terminals,id',
            'departure_time'        => 'required|date',
            'max_weight'            => 'required|integer|min:1',
            'weight_reserved'       => 'nullable|integer|min:0',
            'price_per_kg'          => 'required|numeric|min:0',
            'commission_percentage' => 'nullable|numeric|min:0|max:100',
            'ride_status'           => 'required|string|max:50',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->goodsRideRepository->create($data);
    }

    // Update GoodsRide
    public function updateGoodsRide($id, array $data)
    {
        $validator = Validator::make($data, [
            'transport_type'            => 'sometimes|required|string|max:100',
            'public_terminal_subtype'   => 'nullable|string|max:100',
            'departure_terminal_id'     => 'sometimes|required|exists:terminals,id',
            'arrival_terminal_id'       => 'sometimes|required|exists:terminals,id',
            'departure_time'            => 'sometimes|required|date',
            'max_weight'                => 'sometimes|required|integer|min:1',
            'weight_reserved'           => 'nullable|integer|min:0',
            'price_per_kg'              => 'sometimes|required|numeric|min:0',
            'commission_percentage'     => 'nullable|numeric|min:0|max:100',
            'ride_status'               => 'sometimes|required|string|max:50',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->goodsRideRepository->update($id, $data);
    }

    public function deleteGoodsRide($id) {
        return $this->goodsRideRepository->delete($id);
    }
}
