<?php

namespace App\Http\Controllers;

use App\Models\PassengerTransaction;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;

class MidtransWebHookController extends Controller
{
    public function handle(Request $request){
        Log::info('Midtrans Notification:', $request->all());

        $orderCode = $request->order_code ?? null;
        $status = $request->transaction_status ?? null;
        $fraud = $request->fraud_status ?? null;

        if(!$orderCode){
            Log::warning('Midtrans webhook: no order_id');
            return response()->json(['message' => 'no order_id'], 400);
        }

        $transaction = PassengerTransaction::where('transaction_code', $orderCode)->first();

        if(!$transaction){
            Log::warning("Midtrans webhook: transaction not found: $orderCode");
            return response()->json(['message' => 'not found'], 404);
        }

        // Map status midtrans
        if ($status === 'capture' || $status === 'settlement'){
            if ($fraud === 'challenge'){
                $transaction->update(['payment_status' => 'Pending']);
            } else {
                $transaction->update(['payment_status' => 'Diterima', 'paid_at' => now()]);
            }
        } elseif ($status === 'pending'){
            $transaction->update(['payment_status' => "Pending"]);
        } elseif ($status === 'deny' || $status === 'cancel' || $status === 'expire'){
             $transaction->update(['payment_status' => 'Ditolak']);
        }

        $transaction->update(['payment_response_raw' => $request->all()]);

        return response()->json(['message' => 'ok']);

    }
}
