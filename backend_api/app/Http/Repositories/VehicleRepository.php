<?php

namespace App\Http\Repositories;

use App\Models\Vehicle;

class VehicleRepository
{
    protected $model;

    public function __construct(Vehicle $vehicle)
    {
        $this->model = $vehicle;
    }

    // Ambil semua kendaraan beserta driver
    public function getAll()
    {
        return $this->model
            ->with('driver')
            ->orderBy('created_at', 'DESC')
            ->get();
    }

    // Ambil kendaraan berdasarkan ID
    public function findById($id)
    {
        return $this->model
            ->with('driver')
            ->find($id);
    }

    // Ambil kendaraan berdasarkan driver
    public function getByDriver($driverId)
    {
        return $this->model
            ->where('driver_id', $driverId)
            ->with('driver')
            ->orderBy('created_at', 'DESC')
            ->get();
    }

    public function getDriverById($driverId)
    {
        return $this->model
            ->where('driver_id', $driverId)
            ->with('driver')
            ->first();
    }

    // Tambah kendaraan baru
    public function create(array $data)
    {
        return $this->model->create($data);
    }

    // Update kendaraan
    public function update($id, array $data)
    {
        $vehicle = $this->model->findOrFail($id);
        $vehicle->update($data);
        return $vehicle;
    }

    // Hapus kendaraan
    public function delete($id)
    {
        $vehicle = $this->model->findOrFail($id);
        return $vehicle->delete();
    }
}
