<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use App\Models\GoodsTransaction;
use Illuminate\Support\Facades\Http;

class GoodsCheckMidtransPaymentStatus extends Command
{
    protected $signature = 'midtrans:check-payment';
    protected $description = 'Check and update payment status from Midtrans';

    public function handle()
    {
        $pending = GoodsTransaction::where('payment_status', 'Pending')->get();

        foreach ($pending as $trx) {
            if (!$trx->midtrans_order_id) continue;

            $url = config('midtrans.api_url') . $trx->midtrans_order_id . '/status';

            $response = Http::withBasicAuth(config('midtrans.server_key'), '')
                ->get($url);

            if (!$response->successful()) continue;

            $status = $response->json()['transaction_status'];

            if ($status === 'settlement') {
                $trx->update([
                    'payment_status' => 'Diterima',
                ]);
            } elseif ($status === 'cancel' || $status === 'expire') {
                $trx->update([
                    'payment_status' => 'Ditolak',
                ]);
            }
        }

        $this->info('Midtrans check done');
    }
}
