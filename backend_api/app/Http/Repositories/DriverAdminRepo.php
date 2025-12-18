<?php

namespace App\Http\Repositories;

use App\Helpers\DriverStatusHelper;
use App\Models\Driver;
use Illuminate\Pagination\LengthAwarePaginator;

class DriverAdminRepo
{
    protected $model;

    public function __construct(Driver $driver)
    {
        $this->model = $driver;
    }

    // Ambil semua driver beserta relasinya
    public function getAll()
    {
        return $this->model
            ->with(['user', 'vehicles', 'ratings'])
            ->orderBy('full_name', 'ASC')
            ->get();
    }

    // Ambil driver berdasarkan ID
    public function findById($id)
    {
        return $this->model
            ->with([
                'user',
                'vehicles',
                'passengerRides',
                'goodsRides',
                'driverCommissions',
                'driverWithdrawals',
                'ratings'
            ])
            ->find($id);
    }

    // Buat driver baru
    public function create(array $data)
    {
        return $this->model->create($data);
    }

    // Update data driver
    public function update($id, array $data)
    {
        $driver = $this->model->findOrFail($id);
        $driver->update($data);
        return $driver;
    }

    // Hapus driver
    public function delete($id)
    {
        $driver = $this->model->findOrFail($id);
        return $driver->delete();
    }

    public function updateRating($driverId, $newRating){
        $driver = $this->findById($driverId);

        $driver->total_rating += $newRating;
        $driver->rating_count += 1;
        $driver->average_rating = $driver->total_rating / $driver->rating_count;
        $driver->save();

        return $driver;

    }

    public function paginate($perPage = 10, $filters = [])
{
    $query = $this->model
        ->with('user')
        ->orderBy('created_at', 'DESC');

    // SEARCH
    if (!empty($filters['search'])) {
        $search = $filters['search'];

        $query->where(function($q) use ($search) {
            $q->where('full_name', 'LIKE', "%$search%")
              ->orWhere('telephone', 'LIKE', "%$search%")
              ->orWhereHas('user', function ($u) use ($search) {
                  $u->where('email', 'LIKE', "%$search%");
              });
        });
    }

    // GET ALL FIRST
    $drivers = $query->get();

    // ADD STATUS
    $drivers = $drivers->map(function($driver) {
        $driver->status_driver = DriverStatusHelper::getStatus($driver);
        return $driver;
    });

    // FILTER STATUS
    if (!empty($filters['status_driver'])) {
        $status = $filters['status_driver'];
        $drivers = $drivers->filter(fn($d) => $d->status_driver === $status);
    }

    // PAGINATION SETELAH FILTER – yang benar
    $page = request()->get('page', 1);
    $offset = ($page - 1) * $perPage;

    $paginated = $drivers->slice($offset, $perPage)->values();

    return new LengthAwarePaginator(
        $paginated,
        $drivers->count(),     // total setelah filter (PENTING)
        $perPage,
        $page,
        [
            'path' => request()->url(),
            'query' => request()->query(),
        ]
    );
}

}
