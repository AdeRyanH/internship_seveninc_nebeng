<?php

namespace App\Http\Services;

use App\Http\Repositories\VehicleAdminRepository;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;

class VehicleAdminService
{
    protected $vehicleRepository;

    public function __construct(VehicleAdminRepository $repo)
    {
        $this->vehicleRepository = $repo;
    }

    // List semua kendaraan
    public function listVehicles($perPage = 10, $filters = [])
    {
        return $this->vehicleRepository->paginate($perPage, $filters);
    }

    // Ambil kendaraan berdasarkan ID
    public function getVehicle($id)
    {
        return $this->vehicleRepository->findById($id);
    }

    // Ambil driver

    public function getDriver($driverId)
    {
        return $this->vehicleRepository->getDriver($driverId);
    }

    // List kendaraan per driver
    public function listByDriver($driverId)
    {
        return $this->vehicleRepository->getByDriver($driverId);
    }

    // Tambah kendaraan baru
    public function createVehicle(array $data)
    {
        $validator = Validator::make($data, [
            'driver_id' => 'required|exists:drivers,id',
            'registration_number' => 'required|string|max:255|unique:vehicles,registration_number',
            'registration_year' => 'required|integer|min:1900|max:' . date('Y'),
            'registration_expired' => 'required|date',
            'registration_img' => 'nullable|string',
            'vehicle_name' => 'required|string|max:255',
            'vehicle_color' => 'required|string|max:50',
            'vehicle_type' => 'required|string|in:Motor,Mobil',
            'vehicle_img' => 'nullable|string',
            'vehicle_verified' => 'nullable|boolean',
            'vehicle_rejected_reason' => 'nullable|string|max:255',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        // Default status: belum diverifikasi
        $data['vehicle_verified'] = $data['vehicle_verified'] ?? false;

        return $this->vehicleRepository->create($data);
    }

    // Update kendaraan
    public function updateVehicle($id, array $data)
    {
        $validator = Validator::make($data, [
            'registration_number' => 'sometimes|string|max:255|unique:vehicles,registration_number,' . $id,
            'registration_year' => 'sometimes|integer|min:1900|max:' . date('Y'),
            'registration_expired' => 'sometimes|date',
            'registration_img' => 'nullable|string',
            'vehicle_name' => 'sometimes|string|max:255',
            'vehicle_color' => 'sometimes|string|max:50',
            'vehicle_type' => 'sometimes|string|in:Motor,Mobil',
            'vehicle_img' => 'nullable|string',
            'vehicle_verified' => 'sometimes|boolean',
            'vehicle_rejected_reason' => 'nullable|string|max:255',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->vehicleRepository->update($id, $data);
    }

    // Hapus kendaraan
    public function deleteVehicle($id)
    {
        return $this->vehicleRepository->delete($id);
    }

    // Verifikasi kendaraan
    public function verifyVehicle($id)
    {
        return $this->vehicleRepository->update($id, [
            'vehicle_verified' => true,
            'vehicle_rejected_reason' => null,
        ]);
    }

    // Tolak kendaraan
    public function rejectVehicle($id, string $reason)
    {
        return $this->vehicleRepository->update($id, [
            'vehicle_verified' => false,
            'vehicle_rejected_reason' => $reason,
        ]);
    }
}
