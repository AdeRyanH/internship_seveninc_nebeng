<?php

namespace Database\Seeders;

use Illuminate\Support\Carbon;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;

class DriverWithdrawalSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        //
         $drivers = DB::table('drivers')->pluck('id')->toArray();

        if (empty($drivers)) {
            $this->command->warn('⚠️  Tidak ada data driver di tabel drivers. Jalankan DriverSeeder terlebih dahulu.');
            return;
        }

        $now = Carbon::now();

        DB::table('driver_withdrawals')->insert([
            [
                'driver_id'         => $drivers[0],
                'withdrawal_amount' => 150000,
                'bank_name'         => 'BCA',
                'account_name'      => 'Tanjirou Kamado',
                'account_number'    => 'MIDTRANS_NO_NUMBER',
                'status'            => 'pending',
                'rejected_reason'   => null,
                'created_at'        => $now->copy()->subDays(3),
                'updated_at'        => $now->copy()->subDays(3),
            ],
            [
                'driver_id'         => $drivers[0],
                'withdrawal_amount' => 300000,
                'bank_name'         => 'QRIS',
                'account_name'      => 'Tanjirou Kamado',
                'account_number'    => 'MIDTRANS_NO_NUMBER',
                'status'            => 'diterima',
                'rejected_reason'   => null,
                'created_at'        => $now->copy()->subDays(2),
                'updated_at'        => $now->copy()->subDays(2),
            ],
        ]);

        $this->command->info('✅ DriverWithdrawalSeeder berhasil dijalankan!');
    }
}
