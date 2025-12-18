<?php

namespace App\Http\Controllers;

use App\Http\Resources\UserResource;
use Illuminate\Http\Request;
use App\Http\Services\UserService;
use Illuminate\Validation\ValidationException;

class UserController extends Controller
{
    protected $service;

    public function __construct(UserService $service)
    {
        $this->service = $service;
    }

    public function index(Request $request)
    {
        $perPage = $request->query('perPage', 10);
        $filters = [
            'status' => $request->query('status'),
            'search' => $request->query('search'),
        ];
        $userType = $request->query('role');
        $data = $this->service->list($perPage, $filters,$userType);
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

    public function show($id)
    {
        $user = $this->service->get($id);
        if (!$user) return response()->json(['message' => 'User not found'], 404);
        return response()->json(['data' => $user], 200);
    }

    public function store(Request $request)
    {
        try {
            $user = $this->service->create($request->all());
            return response()->json(['data' => $user], 201);
        } catch (ValidationException $e) {
            return response()->json(['errors' => $e->errors()], 422);
        }
    }

    public function update(Request $request, $id)
    {
        try {
            $user = $this->service->update($id, $request->all());
            return response()->json(['data' => $user], 200);
        } catch (ValidationException $e) {
            return response()->json(['errors' => $e->errors()], 422);
        }
    }

    public function destroy($id)
    {
        $deleted = $this->service->delete($id);
        if (!$deleted) return response()->json(['message' => 'User not found'], 404);
        return response()->json(['message' => 'User deleted'], 200);
    }

    public function verify(Request $request,$id)
    {
        $request->validate([
            'status' => 'required',
        ]);

        try {
            $status = $request->input('status');
            $customer = $this->service->verifyUser($id,$status);
            return response()->json([
                'message' => 'Status Changed', 'data' => $customer
            ], 200);
        } catch (\Throwable $th) {
            return response()->json(['errors' => $th->getMessage()], 422);
        }

    }
}
