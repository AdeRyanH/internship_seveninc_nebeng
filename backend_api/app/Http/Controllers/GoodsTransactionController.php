<?php

namespace App\Http\Controllers;

use Exception;
use Illuminate\Http\Request;
use App\Http\Services\GoodsTransactionService;

class GoodsTransactionController extends Controller
{
    //
    protected $service;

    public function __construct(GoodsTransactionService $service)
    {
        $this->service = $service;
    }

    public function index()
    {
        return response()->json($this->service->listTransactions());
    }

    public function show($id)
    {
        return response()->json($this->service->getTransaction($id));
    }

    public function store(Request $request)
    {
        try {
            $transaction = $this->service->createTransaction($request->all());
            return response()->json([
                'success' => true,
                'message' => 'Transaksi barang berhasil dibuat',
                'data' => $transaction
            ], 201);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'error' => $e->getMessage()
            ], 500);
        }
    }

    public function update(Request $request, $id)
    {
        try {
            $transaction = $this->service->updateTransaction($id, $request->all());
            return response()->json([
                'success' => true,
                'message' => 'Transaksi berhasil diperbarui',
                'data' => $transaction
            ]);
        } catch (Exception $e) {
            return response()->json([
                'success' => false,
                'error' => $e->getMessage()
            ], 500);
        }
    }

    public function updateStatus(Request $request, $id){
        $request->validate(['status' => 'required|string|in:Pending,Diterima,Ditolak,Credited']);
        $transaction = $this->service->updateStatus($id, $request->status);
        return response()->json(['message' => 'Status updated successfully', 'data' => $transaction], 200);
    }

    public function destroy($id)
    {
        try {
            $this->service->deleteTransaction($id);
            return response()->json(['message' => 'Transaksi dihapus']);
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }
}
