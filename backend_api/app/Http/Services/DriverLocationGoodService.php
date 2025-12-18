<?php

namespace App\Http\Services;

use Carbon\Carbon;
use App\Http\Repositories\DriverLocationGoodRepository;
use App\Models\GoodsRide;

class DriverLocationGoodService {
    protected $repository;

    public function __construct(DriverLocationGoodRepository $repo)
    {
        $this->repository = $repo;
    }

    /**
     * DRIVER UPDATE LOCATION
     */
    public function updateLocation(
        int $goodId,
        int $driverId,
        float $latitude,
        float $longitude
    ): array {
        $good = GoodsRide::find($goodId);
        if (! $good) {
            return [
                'success' => false,
                'message' => 'Goods ride not found',
                'code'    => 404
            ];
        }

        if ($good->driver_id !== $driverId) {
            return [
                'success' => false,
                'message' => 'Goods ride does not belong to this driver',
                'code'    => 403
            ];
        }

        if (! in_array($good->ride_status, ['pending', 'dalam_perjalanan'], true)) {
            return [
                'success' => false,
                'message' => 'Goods ride is not active',
                'code'    => 409
            ];
        }

        $location = $this->repository->updateOrCreate(
            ['good_id' => $good->id],
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
    public function getActiveLocation(int $goodId)
    {
        return $this->repository->findByGood($goodId);
    }

    /**
     * STOP TRACKING
     */
    public function stopTracking(int $goodId): void
    {
        $this->repository->deactivate($goodId);
    }
}
