<?php

namespace App\Http\Services;

use Carbon\Carbon;
use App\Models\PassengerRide;
use App\Http\Repositories\DriverLocationRideRepository;

class DriverLocationRideService {
    protected $repository;

    public function __construct(DriverLocationRideRepository $repo)
    {
        $this->repository = $repo;
    }

    /**
     * DRIVER UPDATE LOCATION
     */
    public function updateLocation(
        int $rideId,
        int $driverId,
        float $latitude,
        float $longitude
    ) {
        // VALIDASI: ride harus milik driver
        $ride = PassengerRide::where('id', $rideId)
            ->where('driver_id', $driverId)
            ->whereNotIn('ride_status', ['selesai', 'dibatalkan'])
            ->firstOrFail();

        return $this->repository->updateOrCreate(
            ['ride_id' => $ride->id],
            [
                'driver_id'    => $driverId,
                'latitude'     => $latitude,
                'longitude'    => $longitude,
                'last_seen_at' => Carbon::now(),
                'is_active'    => true,
            ]
        );
    }

    /**
     * CUSTOMER GET DRIVER LOCATION
     */
    public function getActiveLocation(int $rideId)
    {
        return $this->repository->findByRide($rideId);
    }

    /**
     * STOP TRACKING
     */
    public function stopTracking(int $rideId): void
    {
        $this->repository->deactivate($rideId);
    }
}
