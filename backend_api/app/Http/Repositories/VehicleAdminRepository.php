<?php

namespace App\Http\Repositories;

use App\Models\Vehicle;

class VehicleAdminRepository
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

    // Ambil driver
    public function getDriver($driverId){
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

    public function paginate($perPage = 10, $filters = [])
    {
        $query = $this->model
            ->with('driver')
            ->orderBy('created_at', 'DESC');

            // filter
            if(!empty($filters['status'])) {
                $query->where('verified', $filters['status']);
            }

            // search
            if(!empty($filters['search'])) {
                $search = $filters['search'];

                $query->where(function($q) use ($search) {
                $q->where('vehicle_name', 'LIKE', "%$search%")
                  ->orWhere('vehicle_type', 'LIKE', "%$search%")
                  ->orWhere('registration_number', 'LIKE', "%$search%")
                  ->orWhere('vehicle_color', 'LIKE', "%$search%")
                  ->orWhereHas('driver', function ($u) use ($search){
                    $u->where('full_name', 'LIKE', "%$search%");
                  });
            });
            }

        return $query->paginate($perPage);
    }
}
