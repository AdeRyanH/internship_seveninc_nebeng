<?php

namespace App\Http\Services;

use Carbon\Carbon;
use App\Models\PassengerRide;
use App\Http\Repositories\DriverLocationRideRepository;
use Symfony\Component\HttpKernel\Exception\HttpException;

class DriverLocationRideService {
    protected $repository;

    public function __construct(DriverLocationRideRepository $repo)
    {
        $this->repository = $repo;
    }

    /**
     * DRIVER UPDATE LOCATION
     */
    // public function updateLocation(
    //     int $rideId,
    //     int $driverId,
    //     float $latitude,
    //     float $longitude
    // ) {
    //     // VALIDASI: ride harus milik driver
    //     $ride = PassengerRide::where('id', $rideId)
    //         ->where('driver_id', $driverId)
    //         ->whereNotIn('ride_status', ['selesai', 'dibatalkan'])
    //         ->firstOrFail();

    //     return $this->repository->updateOrCreate(
    //         ['ride_id' => $ride->id],
    //         [
    //             'driver_id'    => $driverId,
    //             'latitude'     => $latitude,
    //             'longitude'    => $longitude,
    //             'last_seen_at' => Carbon::now(),
    //             'is_active'    => true,
    //         ]
    //     );
    // }
    public function updateLocation(
        int $rideId,
        int $driverId,
        float $latitude,
        float $longitude
    ): array {
        // 1. Cek ID ada atau tidak
        $ride = PassengerRide::find($rideId);
        if (! $ride) {
            return [
                'success' => false,
                'message' => 'Ride not found',
                'code'    => 404
            ];
        }

        // 2. Cek kepemilikan
        if ($ride->driver_id !== $driverId) {
            return [
                'success' => false,
                'message' => 'Ride does not belong to this driver',
                'code'    => 403
            ];
        }

        // 3. Cek status ride
        if (! in_array($ride->ride_status, ['pending', 'dalam_perjalanan'], true)) {
            return [
                'success' => false,
                'message' => 'Ride is not active',
                'code'    => 409
            ];
        }

        // 4. Simpan lokasi
        $location = $this->repository->updateOrCreate(
            ['ride_id' => $ride->id],
            [
                'driver_id'    => $driverId,
                'latitude'     => $latitude,
                'longitude'    => $longitude,
                'last_seen_at' => Carbon::now(),
                'is_active'    => true,
            ]
        );

        return [
            'success' => true,
            'data'    => $location,
            'code'    => 200
        ];
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
