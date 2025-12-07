<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;
use App\Http\Services\PassengerTransactionService;

class PassengerTransactionController extends Controller
{
    //
    protected $transactionService;

    public function __construct(PassengerTransactionService $service)
    {
        $this->transactionService = $service;
    }

    // GET /api/passenger-transactions
    public function index()
    {
        $transactions = $this->transactionService->listTransactions();
        return response()->json(['data' => $transactions], 200);
    }

    // GET /api/passenger-transactions/{id}
    public function show($id)
    {
        $transaction = $this->transactionService->getTransaction($id);
        if (!$transaction) {
            return response()->json(['message' => 'Transaction not found'], 404);
        }
        return response()->json(['data' => $transaction], 200);
    }

    // GET /api/passenger-transactions/customer/{customer_id}
    public function byCustomer($customerId)
    {
        $transactions = $this->transactionService->listByCustomer($customerId);
        return response()->json(['data' => $transactions], 200);
    }

    // GET /api/passenger-transactions/booking/{booking_id}
    public function byBooking($bookingId)
    {
        $transaction = $this->transactionService->getByBooking($bookingId);
        return response()->json(['data' => $transaction], 200);
    }

    // POST /api/passenger-transactions
    // public function store(Request $request)
    // {
    //     try {
    //         $transaction = $this->transactionService->createTransaction($request->all());

    //         return response()->json([
    //                     'data' => [
    //                         'id' => $transaction->id,
    //                         'passenger_ride_booking_id' => $transaction->passenger_ride_booking_id,
    //                         'customer_id' => $transaction->customer_id,
    //                         'total_amount' => $transaction->total_amount,
    //                         'payment_method_id' => $transaction->payment_method_id,
    //                         'payment_proof_img' => $transaction->payment_proof_img,
    //                         'payment_status' => $transaction->payment_status,
    //                         'credit_used' => $transaction->credit_used,
    //                         'transaction_date' => $transaction->transaction_date,
    //                         'created_at' => $transaction->created_at,
    //                         'updated_at' => $transaction->updated_at,
    //                     ]
    //                 ], 201);

    //     } catch (ValidationException $e) {
    //         return response()->json(['errors' => $e->errors()], 422);
    //     }
    // }
    // POST /api/passenger-transactions
    public function store(Request $request)
    {
        try {
            $data = $request->all();
            unset($data['payment_status']); // HARD RULE

            // $transaction = $this->transactionService->createTransaction($request->all());
            $transaction = $this->transactionService->createTransaction($data);

            return response()->json(data: ['data' => $transaction], status: 201);

        } catch (ValidationException $e) {
            return response()->json(data: ['errors' => $e->errors()], status: 422);
        }
    }

    // PUT /api/passenger-transactions/{id}
    public function update(Request $request, $id)
    {
        try {
            $transaction = $this->transactionService->updateTransaction($id, $request->all());
            return response()->json(['data' => $transaction], 200);
        } catch (ValidationException $e) {
            return response()->json(['errors' => $e->errors()], 422);
        }
    }

    // PATCH /api/passenger-transactions/{id}/status
    public function updateStatus(Request $request, $id)
    {
        $request->validate(['status' => 'required|string|in:pending,diterima,ditolak,credited']);
        $transaction = $this->transactionService->updateStatus($id, $request->status);
        return response()->json(['message' => 'Status updated successfully', 'data' => $transaction], 200);
    }

    // DELETE /api/passenger-transactions/{id}
    public function destroy($id)
    {
        $deleted = $this->transactionService->deleteTransaction($id);
        if ($deleted) {
            return response()->json(['message' => 'Transaction deleted successfully'], 200);
        }
        return response()->json(['message' => 'Transaction not found'], 404);
    }
}
