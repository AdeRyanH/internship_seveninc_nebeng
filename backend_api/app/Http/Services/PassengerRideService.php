<?php

namespace App\Http\Services;

use App\Http\Repositories\PassengerRideRepository;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;

class PassengerRideService
{
    protected $passengerRideRepository;

    public function __construct(PassengerRideRepository $repo)
    {
        $this->passengerRideRepository = $repo;
    }

    // Ambil semua ride
    public function listRides()
    {
        return $this->passengerRideRepository->getAll();
    }

    // Ambil ride by ID
    public function getRide($id)
    {
        return $this->passengerRideRepository->findById($id);
    }

    // Ambil ride berdasarkan driver
    public function listByDriver($driverId)
    {
        return $this->passengerRideRepository->getByDriver($driverId);
    }

    // Ambil ride berdasarkan status
    public function listByStatus($status)
    {
        return $this->passengerRideRepository->getByStatus($status);
    }

    // Tambah ride baru
    public function createRide(array $data)
    {
        $validator = Validator::make($data, [
            'driver_id'             => 'required|exists:drivers,id',
            'vehicle_type'          => 'required|string|in:motor,mobil',
            'departure_terminal_id' => 'required|exists:terminals,id',
            'arrival_terminal_id'   => 'required|exists:terminals,id',
            'departure_time'        => 'required|date',
            'seats_available'       => 'required|integer|min:1',
            'seats_reserved'        => 'nullable|integer|min:0',
            'price_per_seat'        => 'required|numeric|min:0',
            'commission_percentage' => 'nullable|numeric|min:0|max:100',
            'ride_status'           => 'required|string|in:pending,dalam_perjalanan,selesai,dibatalkan',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->passengerRideRepository->create($data);
    }

    // Update ride
    public function updateRide($id, array $data)
    {
        $validator = Validator::make($data, [
            'vehicle_type'          => 'sometimes|string|in:motor,mobil',
            'departure_terminal_id' => 'sometimes|exists:terminals,id',
            'arrival_terminal_id'   => 'sometimes|exists:terminals,id',
            'departure_time'        => 'sometimes|date',
            'seats_available'       => 'sometimes|integer|min:1',
            'seats_reserved'        => 'nullable|integer|min:0',
            'price_per_seat'        => 'sometimes|numeric|min:0',
            'commission_percentage' => 'nullable|numeric|min:0|max:100',
            'ride_status'           => 'sometimes|string|in:pending,dalam_perjalanan,selesai,dibatalkan',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->passengerRideRepository->update($id, $data);
    }

    // Hapus ride
    public function deleteRide($id)
    {
        return $this->passengerRideRepository->delete($id);
    }
}
