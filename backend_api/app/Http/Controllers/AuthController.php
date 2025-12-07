<?php

namespace App\Http\Controllers;

use App\Http\Services\AuthService;
use Exception;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;

class AuthController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    protected $authService;

    public function __construct(AuthService $authService){
        $this->authService = $authService;
    }

    public function register(Request $request){
        try {
            $valiated = $request->validate([
                'name'      => 'required|string|max:255',
                'username'  => 'required|string|max:255|unique:users',
                'email'     => 'required|string|email|max:255|unique:users',
                'password'  => 'required|string|min:8|confirmed',
                // 'role' => 'required|in:admin,superadmin,customer,driver,terminal',
                'user_type' => 'required|in:admin,superadmin,customer,driver,terminal,finance',
            ]);

            $data = $this->authService->register($valiated);
            return response()->json([
                'success' => true,
                'message' => "User registered successfully",
                'data' => $data
            ], 201);
        } catch (ValidationException $e) {
            return response()->json([
                'success' => false,
                'message' => 'Registration failed : '. $e->getMessage()
            ], 500);

        } catch (\Throwable $e){
            return response()->json([
                'message' => "registraion Failed",
                'error' => $e->getMessage(),
                'trace' => $e->getTraceAsString(),
            ], 500);
        }

    }

    public function login(Request $request){
        try {
            $validated = $request->validate([
            'userIdentifier' => 'required|string',
            'password' => 'required|string'
        ]);

        $data = $this->authService->login($validated);
        return response()->json([
            'success' => true,
            'message' => 'Login successfully',
            'data' => $data
        ], 200);

        } catch (ValidationException $e) {
            return response()->json([
                'success' => false,
                'message' => 'Invalid credential',
                'error' => $e->errors()
            ], 422);

        } catch (ModelNotFoundException $e) {
            return response()->json([
                'success' => false,
                'message' => 'User not found'
            ], 500);

        }catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'Login failed : '. $e->getMessage()
            ], 500);

        } catch (\Throwable $e) {
            return response()->json([
                'message' => "login Failed",
                'error' => $e->getMessage(),
                'trace' => $e->getTraceAsString(),
            ], 500);
        }


    }

    public function logout(Request $request){
        try {
            $this->authService->logout($request->user());
            return response()->json([
                'success' => true,
                'message' => 'Logout Successully'
            ]);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'Logout failed' . $e->getMessage()
            ], 500);
        }

    }
    public function index()
    {
        //
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
    public function show(string $id)
    {
        //
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
