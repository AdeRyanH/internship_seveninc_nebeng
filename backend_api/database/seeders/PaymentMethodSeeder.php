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
    public function run()
{
    $methods = [
        // Virtual Account
        ['code' => 'bni', 'name' => 'BNI Virtual Account', 'method' => 'bank_transfer'],
        ['code' => 'bri', 'name' => 'BRI Virtual Account', 'method' => 'bank_transfer'],
        ['code' => 'bca', 'name' => 'BCA Virtual Account', 'method' => 'bank_transfer'],
        ['code' => 'mandiri', 'name' => 'Mandiri Virtual Account', 'method' => 'bank_transfer'],

        // QRIS
        ['code' => 'qris', 'name' => 'QRIS', 'method' => 'qris'],

        // E-Wallets
        ['code' => 'gopay', 'name' => 'GoPay', 'method' => 'gopay'],
        ['code' => 'shopeepay', 'name' => 'ShopeePay', 'method' => 'shopeepay'],
        ['code' => 'ovo', 'name' => 'OVO', 'method' => 'ovo'],
    ];

    foreach ($methods as $method) {
        PaymentMethod::updateOrCreate(
            ['code' => $method['code']],
            $method
        );
    }

    $this->command->info("PaymentMethodSeeder Midtrans Loaded!");
}


}
