<?php

namespace App\Http\Controllers;

use App\Http\Services\RideService;
use Illuminate\Http\Request;

class RideController extends Controller
{
    protected $rideService;

    public function __construct(RideService $rideService){
        $this->rideService = $rideService;
    }

    /**
     * Display a listing of the resource.
     */
    public function index(Request $request)
    {
        $perPage = $request->input('perPage', 10);
        $filters = [
            'search' => $request->query('search'),
        ];

        $data = $this->rideService->listAllRides($perPage);
        return response()->json([
            'success' => true,
            'data' => $data->items(),
            'meta' => [
            'current_page' => $data->currentPage(),
            'last_page' => $data->lastPage(),
            'per_page' => $data->perPage(),
            'total' => $data->total(),
            ],
            'links' => [
                'next_page_url' => $data->nextPageUrl(),
                'prev_page_url' => $data->previousPageUrl(),
                'first' => $data->url(1),
                'last' => $data->url($data->lastPage()),
            ],

    ]);
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show($type, $id)
    {
        try {
            $ride = $this->rideService->getDetailRide($type, $id);
            return response()->json([
                'success' => 'true',
                'data' => $ride
            ]);
        } catch (\InvalidArgumentException $e) {
            return response()->json([
                'success' => false,
                'message' => "Detail :",$e->getMessage()
            ]);
        }
    }

    public function byDriver($driverId){
        try {
            $ride = $this->rideService->listByDriver($driverId);
            return response()->json([
                'success' => 'true',
                'data' => $ride
        ]);
        } catch (\Throwable $e) {
            return response()->json([
                'success' => 'false',
                'message' => "byDriver :", $e->getMessage()
        ]);
        }

    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(string $id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }
}
