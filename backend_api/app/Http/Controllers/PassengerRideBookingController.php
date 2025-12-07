<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;
use App\Http\Services\PassengerRideBookingService;

use function Laravel\Prompts\error;

class PassengerRideBookingController extends Controller
{
    //
    protected $bookingService;

    public function __construct(PassengerRideBookingService $service)
    {
        $this->bookingService = $service;
    }

    // GET /api/passenger-ride-bookings
    public function index()
    {
        $bookings = $this->bookingService->listBookings();
        return response()->json(['data' => $bookings], 200);
    }

    // GET /api/passenger-ride-bookings/{id}
    public function show($id)
    {
        $booking = $this->bookingService->getBooking($id);
        if (!$booking) {
            return response()->json(['message' => 'Booking not found'], 404);
        }
        return response()->json(['data' => $booking], 200);
    }

    // GET /api/passenger-ride-bookings/ride/{ride_id}
    public function byRide($rideId)
    {
        $bookings = $this->bookingService->listByRide($rideId);
        return response()->json(['data' => $bookings], 200);
    }

    // GET /api/passenger-ride-bookings/customer/{customer_id}
    public function byCustomer($customerId)
    {
        $bookings = $this->bookingService->listByCustomer($customerId);
        return response()->json(['data' => $bookings], 200);
    }

    // POST /api/passenger-ride-bookings
    public function store(Request $request)
    {
        try {
            $booking = $this->bookingService->createBooking($request->all());
            return response()->json(['data' => $booking], 201);
        } catch (ValidationException $e) {
            return response()->json(['errors' => $e->errors()], 422);
        } catch (\Throwable $er){
            return response()->json(['errors' => $er], 500);
        }

    }

    // PUT /api/passenger-ride-bookings/{id}
    public function update(Request $request, $id)
    {
        try {
            $booking = $this->bookingService->updateBooking($id, $request->all());
            return response()->json(['data' => $booking], 200);
        } catch (ValidationException $e) {
            return response()->json(['errors' => $e->errors()], 422);
        }
    }

    // PATCH /api/passenger-ride-bookings/{id}/status
    public function updateStatus(Request $request, $id)
    {
        $request->validate(['status' => 'required|string|in:pending,diterima,ditolak']);
        $booking = $this->bookingService->updateStatus($id, $request->status);
        return response()->json(['message' => 'Status updated successfully', 'data' => $booking], 200);
    }

    // DELETE /api/passenger-ride-bookings/{id}
    public function destroy($id)
    {
        $deleted = $this->bookingService->deleteBooking($id);
        if ($deleted) {
            return response()->json(['message' => 'Booking deleted successfully'], 200);
        }
        return response()->json(['message' => 'Booking not found'], 404);
    }
}
