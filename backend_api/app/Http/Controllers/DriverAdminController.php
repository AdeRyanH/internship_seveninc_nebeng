<?php

namespace App\Http\Controllers;

use App\Http\Resources\DriverAdminResource;
use App\Http\Services\DriverAdminService;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;

class DriverAdminController extends Controller
{
    //
     protected $driverService;

    public function __construct(DriverAdminService $driverService)
    {
        $this->driverService = $driverService;
    }

    // GET /api/drivers
    public function index(Request $request)
    {
        $perPage = $request->query('perPage', 10);
        $filters = [
            'search' => $request->query('search'),
            'status_driver' => $request->query('status_driver'),
        ];
        $drivers = $this->driverService->listDrivers($perPage, $filters);
        $data = DriverAdminResource::collection($drivers);
        return response()->json([
             'success' => true,
            'data' => $data->items(),
            'meta' => [
            'current_page' => $drivers->currentPage(),
            'last_page' => $drivers->lastPage(),
            'per_page' => $drivers->perPage(),
            'total' => $drivers->total(),
            ],
            'links' => [
                'next_page_url' => $drivers->nextPageUrl(),
                'prev_page_url' => $drivers->previousPageUrl(),
                'first' => $drivers->url(1),
                'last' => $drivers->url($drivers->lastPage()),
            ],
        ]);
    }

    // GET /api/drivers/{id}
    public function show($id)
    {
        $driver = $this->driverService->getDriver($id);
        if (!$driver) {
            return response()->json(['message' => 'Driver not found'], 404);
        }
        return response()->json(['data' => $driver], 200);
    }

    // POST /api/drivers
    public function store(Request $request)
    {
        try {
            $driver = $this->driverService->createDriver($request->all());
            return response()->json(['data' => $driver], 201);
        } catch (ValidationException $e) {
            return response()->json(['errors' => $e->errors()], 422);
        }
    }

    // PUT /api/drivers/{id}
    public function update(Request $request, $id)
    {
        try {
            $driver = $this->driverService->updateDriver($id, $request->all());
            return response()->json(['data' => $driver], 200);
        } catch (ValidationException $e) {
            return response()->json(['errors' => $e->errors()], 422);
        }
    }

    // DELETE /api/drivers/{id}
    public function destroy($id)
    {
        $deleted = $this->driverService->deleteDriver($id);
        if ($deleted) {
            return response()->json(['message' => 'Driver deleted successfully'], 200);
        }
        return response()->json(['message' => 'Driver not found'], 404);
    }

    public function verify(Request $request, $id){
        $validated = $request->validate([
            'type' => 'required|in:id_card,driver_license,police_clearance',
            'status' => 'required',
            'reason' => 'nullable|string|max:255'
        ]);

        return $this->driverService->verifyDocument(
            $id,
            $validated['type'],
            $validated['status'],
            $validated['reason'] ?? null
        );
    }
}
