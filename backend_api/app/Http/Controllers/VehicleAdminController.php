<?php

namespace App\Http\Controllers;

use App\Http\Services\VehicleAdminService;
use Illuminate\Http\Request;
use App\Http\Services\VehicleService;
use Illuminate\Validation\ValidationException;

class VehicleAdminController extends Controller
{
    //
    protected $vehicleService;

    public function __construct(VehicleAdminService $service)
    {
        $this->vehicleService = $service;
    }

    // GET /api/vehicles
    public function index(Request $request)
    {
        $perPage = $request->query('perPage', 10);
        $filters = [
            'search' => $request->query('search'),
            'status' => $request->query('status'),
        ];

        $data = $this->vehicleService->listVehicles($perPage, $filters);
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

    // GET /api/vehicles/{id}
    public function show($id)
    {
        $vehicle = $this->vehicleService->getVehicle($id);
        if (!$vehicle) {
            return response()->json(['message' => 'Vehicle not found'], 404);
        }
        return response()->json(['data' => $vehicle], 200);
    }

    // GET /api/vehicles/driver/{driver_id}
    public function byDriver($driverId)
    {
        $vehicles = $this->vehicleService->listByDriver($driverId);
        return response()->json(['data' => $vehicles], 200);
    }

    // GET
    public function getDriver($driverId)
    {
        $driver = $this->vehicleService->getDriver($driverId);
        return response()->json(['data' => $driver], 200);
    }

    // POST /api/vehicles
    public function store(Request $request)
    {
        try {
            $vehicle = $this->vehicleService->createVehicle($request->all());
            return response()->json(['data' => $vehicle], 201);
        } catch (ValidationException $e) {
            return response()->json(['errors' => $e->errors()], 422);
        }
    }

    // PUT /api/vehicles/{id}
    public function update(Request $request, $id)
    {
        try {
            $vehicle = $this->vehicleService->updateVehicle($id, $request->all());
            return response()->json(['data' => $vehicle], 200);
        } catch (ValidationException $e) {
            return response()->json(['errors' => $e->errors()], 422);
        }
    }

    // PATCH /api/vehicles/{id}/verify
    public function verify($id)
    {
        $vehicle = $this->vehicleService->verifyVehicle($id);
        return response()->json(['message' => 'Vehicle verified successfully', 'data' => $vehicle], 200);
    }

    // PATCH /api/vehicles/{id}/reject
    public function reject(Request $request, $id)
    {
        $request->validate(['reason' => 'required|string|max:255']);
        $vehicle = $this->vehicleService->rejectVehicle($id, $request->reason);
        return response()->json(['message' => 'Vehicle rejected', 'data' => $vehicle], 200);
    }

    // DELETE /api/vehicles/{id}
    public function destroy($id)
    {
        $deleted = $this->vehicleService->deleteVehicle($id);
        if ($deleted) {
            return response()->json(['message' => 'Vehicle deleted successfully'], 200);
        }
        return response()->json(['message' => 'Vehicle not found'], 404);
    }
}
