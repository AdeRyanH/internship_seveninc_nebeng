<?php

namespace App\Http\Controllers;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Services\DriverLocationService;

class DriverLocationController extends Controller
{
    protected DriverLocationService $service;

    public function __construct(DriverLocationService $service)
    {
        $this->service = $service;
    }

    /**
     * DRIVER → POST lokasi
     */
    public function store(Request $request)
    {
        $data = $request->validate([
            'ride_id'   => 'required|integer',
            'driver_id' => 'required|integer',
            'latitude'  => 'required|numeric',
            'longitude' => 'required|numeric',
        ]);

        $location = $this->service->updateLocation($data);

        return response()->json([
            'success' => true,
            'data' => $location,
        ]);
    }

    /**
     * CUSTOMER → GET lokasi driver
     */
    public function show(int $rideId)
    {
        $location = $this->service->getByRide($rideId);

        if (!$location) {
            return response()->json([
                'success' => false,
                'message' => 'Driver location not found or inactive',
            ], 404);
        }

        return response()->json([
            'success' => true,
            'data' => [
                'ride_id'      => $location->ride_id,
                'driver_id'    => $location->driver_id,
                'latitude'     => $location->latitude,
                'longitude'    => $location->longitude,
                'last_seen_at' => $location->last_seen_at,
            ]
        ]);
    }
}
