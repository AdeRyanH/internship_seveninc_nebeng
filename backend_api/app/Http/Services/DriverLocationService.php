<?php
// app/Services/DriverLocationService.php

namespace App\Services;

use App\Models\DriverLocation;
use Carbon\Carbon;

class DriverLocationService
{
    /**
     * Update / create driver location by ride_id
     */
    public function updateLocation(array $data): DriverLocation
    {
        return DriverLocation::updateOrCreate(
            ['ride_id' => $data['ride_id']],
            [
                'driver_id'    => $data['driver_id'],
                'latitude'     => $data['latitude'],
                'longitude'    => $data['longitude'],
                'last_seen_at' => Carbon::now(),
                'is_active'    => true,
            ]
        );
    }

    /**
     * Get current driver location for customer
     */
    public function getByRide(int $rideId): ?DriverLocation
    {
        return DriverLocation::where('ride_id', $rideId)
            ->where('is_active', true)
            ->first();
    }

    /**
     * Stop tracking when ride finished / cancelled
     */
    public function deactivate(int $rideId): void
    {
        DriverLocation::where('ride_id', $rideId)
            ->update(['is_active' => false]);
    }
}
