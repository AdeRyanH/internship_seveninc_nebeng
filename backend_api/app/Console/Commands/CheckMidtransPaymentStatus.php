<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use App\Models\PassengerRideBooking;
use App\Models\PassengerTransaction;
use Illuminate\Support\Facades\Http;

// tidak dipanggil oleh file manapun (dijalankan otomatis oleh scheduler)
// php artisan midtrans:check-payment
class CheckMidtransPaymentStatus extends Command
{
    protected $signature = 'midtrans:check-payment';
    protected $description = 'Check and update payment status from Midtrans';

    public function handle()
    {
        // $pending = PassengerTransaction::where('payment_status', 'Pending')->get();

        // foreach ($pending as $trx) {
        //     if (!$trx->midtrans_order_id) continue;

        //     $url = config('midtrans.api_url') . $trx->midtrans_order_id . '/status';

        //     $response = Http::withBasicAuth(config('midtrans.server_key'), '')
        //         ->get($url);

        //     // if (!$response->successful()) continue;
        //     // Log jika request gagal
        //     if (!$response->successful()) {
        //         logger("MIDTRANS CHECK FAILED", [
        //             'order_id' => $trx->midtrans_order_id,
        //             'response' => $response->body()
        //         ]);
        //         continue;
        //     }

        //     $status = $response->json()['transaction_status'];

        //     if ($status === 'settlement') {
        //         $trx->update([
        //             'payment_status' => 'Diterima',
        //         ]);
        //     } elseif ($status === 'cancel' || $status === 'expire') {
        //         $trx->update([
        //             'payment_status' => 'Ditolak',
        //         ]);
        //     }
        // }

        // $this->info('Midtrans check done');
         // ✅ HANYA transaksi MIDTRANS & Pending
        $transactions = PassengerTransaction::where('payment_status', 'pending')
            ->whereNotNull('midtrans_order_id')
            ->get();

        foreach ($transactions as $trx) {

            $response = Http::withBasicAuth(
                config('midtrans.server_key'),
                ''
            )->get(
                config('midtrans.api_url') . $trx->midtrans_order_id . '/status'
            );

            if (!$response->successful()) {
                logger()->warning('MIDTRANS CHECK FAILED', [
                    'order_id' => $trx->midtrans_order_id,
                    'http'     => $response->status(),
                    'body'     => $response->body()
                ]);
                continue;
            }

            $json = $response->json();
            $status = $json['transaction_status'] ?? null;

            switch ($status) {

                case 'settlement':
                case 'capture': // credit card
                    $trx->update([
                        'payment_status' => 'diterima',
                        'payment_response_raw' => $json
                    ]);

                    // ✅ UPDATE BOOKING (WAJIB)
                    PassengerRideBooking::where('id', $trx->passenger_ride_booking_id)
                        ->update(['status' => 'diterima']);
                    break;

                case 'expire':
                case 'cancel':
                case 'deny':
                    $trx->update([
                        'payment_status' => 'ditolak',
                        'payment_response_raw' => $json
                    ]);

                    // ✅ UPDATE BOOKING
                    PassengerRideBooking::where('id', $trx->passenger_ride_booking_id)
                        ->update(['status' => 'ditolak']);
                    break;

                default:
                    // pending / authorize / challenge → biarkan
                    break;
            }
        }

        $this->info('Midtrans payment status check completed');
    }
}
