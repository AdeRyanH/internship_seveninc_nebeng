<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Http\Services\DriverLocationGoodService;

class DriverLocationGoodController extends Controller
{
    //
    protected $service;

    public function __construct(DriverLocationGoodService $service)
    {
        $this->service = $service;
    }

    /**
     * DRIVER — SEND REALTIME LOCATION
     */
    public function store(Request $request, int $good_id)
    {
        $request->validate([
            'latitude'  => 'required|numeric',
            'longitude' => 'required|numeric',
        ]);

        $user = Auth::user();

        if ($user->user_type !== 'driver' || ! $user->driver) {
            return response()->json([
                'success' => false,
                'message' => 'Only driver can update location'
            ], 403);
        }

        $result = $this->service->updateLocation(
            goodId: $good_id,
            driverId: $user->driver->id,
            latitude: $request->latitude,
            longitude: $request->longitude
        );

        return response()->json(
            [
                'success' => $result['success'],
                'message' => $result['message'] ?? null,
                'data'    => $result['data'] ?? null,
            ],
            $result['code']
        );
    }

    /**
     * CUSTOMER — FETCH DRIVER LOCATION
     */
    public function show(int $good_id)
    {
        $location = $this->service->getActiveLocation($good_id);

        if (!$location) {
            return response()->json([
                'success' => false,
                'message' => 'Driver location not available'
            ], 404);
        }

        return response()->json([
            'success' => true,
            'data'    => $location
        ]);
    }
}
