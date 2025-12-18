<?php

namespace Database\Seeders;

use App\Models\PaymentMethod;
use Illuminate\Database\Seeder;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;

class PaymentMethodSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        //
        $methods = [
            [
                'bank_name'      => 'QRIS',
                'account_name'   => 'MIDTRANS_QRIS',
                'account_number' => 'MIDTRANS_NO_NUMBER',
            ],
            [
                'bank_name'      => 'BRI',
                'account_name'   => 'MIDTRANS_BRI',
                'account_number' => 'MIDTRANS_NO_NUMBER',
            ],
            [
                'bank_name'      => 'BCA',
                'account_name'   => 'MIDTRANS_BCA',
                'account_number' => 'MIDTRANS_NO_NUMBER',
            ],
            [
                'bank_name'      => 'DANA',
                'account_name'   => 'MIDTRANS_DANA',
                'account_number' => 'MIDTRANS_NO_NUMBER',
            ]
        ];

        // foreach ($methods as $method) {
        //     PaymentMethod::updateOrCreate(
        //         ['account_number' => $method['account_number']],
        //         $method
        //     );
        // }
        foreach ($methods as $method) {
            PaymentMethod::updateOrCreate(
                ['bank_name' => $method['bank_name']], // ✅ UNIQUE KEY
                $method
            );
        }

    $this->command->info("PaymentMethodSeeder Midtrans Loaded!");
}


}
