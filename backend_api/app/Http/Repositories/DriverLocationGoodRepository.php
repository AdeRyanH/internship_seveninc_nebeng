<?php

namespace App\Http\Repositories;

use App\Models\DriverLocationGood;

class DriverLocationGoodRepository
{
    protected $model;

    /**
     * Inject DriverLocation model instance.
     */
    public function __construct(DriverLocationGood $model)
    {
        $this->model = $model;
    }

    /**
     * Find active location by ride ID.
     */
    public function findByRide(int $rideId): ?DriverLocationGood
    {
        return $this->model
            ->where('ride_id', $rideId)
            ->first();
    }

    /**
     * Update or create location record.
     */
    public function updateOrCreate(array $where, array $data): DriverLocationGood
    {
        return $this->model->updateOrCreate($where, $data);
    }

    /**
     * Deactivate tracking for ride.
     */
    public function deactivate(int $rideId): void
    {
        $this->model
            ->where('ride_id', $rideId)
            ->update(['is_active' => false]);
    }
}
