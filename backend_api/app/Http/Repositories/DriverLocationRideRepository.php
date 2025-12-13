<?php

namespace App\Http\Repositories;

use App\Models\DriverLocationRide;

class DriverLocationRideRepository
{
    // protected $model;

    // /**
    //  * Inject DriverLocation model instance.
    //  */
    // public function __construct(DriverLocationRide $model)
    // {
    //     $this->model = $model;
    // }

    // /**
    //  * Find active location by ride ID.
    //  */
    // public function findByRide(int $rideId): ?DriverLocationRide
    // {
    //     return $this->model
    //         ->where('ride_id', $rideId)
    //         ->first();
    // }

    // /**
    //  * Update or create location record.
    //  */
    // public function updateOrCreate(array $where, array $data): DriverLocationRide
    // {
    //     return $this->model->updateOrCreate($where, $data);
    // }

    // /**
    //  * Deactivate tracking for ride.
    //  */
    // public function deactivate(int $rideId): void
    // {
    //     $this->model
    //         ->where('ride_id', $rideId)
    //         ->update(['is_active' => false]);
    // }
    /**
     * Find location by ride ID.
     */
    public function findByRide(int $rideId): ?DriverLocationRide
    {
        return DriverLocationRide::where('ride_id', $rideId)->first();
    }

    /**
     * Update or create location (STATELESS).
     */
    public function updateOrCreate(array $where, array $data): DriverLocationRide
    {
        return DriverLocationRide::updateOrCreate($where, $data);
    }

    /**
     * Deactivate tracking.
     */
    public function deactivate(int $rideId): void
    {
        DriverLocationRide::where('ride_id', $rideId)
            ->update(['is_active' => false]);
    }
}
