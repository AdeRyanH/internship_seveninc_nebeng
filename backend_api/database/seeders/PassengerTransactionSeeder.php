<?php

namespace Database\Seeders;

use Carbon\Carbon;
use App\Models\Customer;
use App\Models\PaymentMethod;
use Illuminate\Database\Seeder;
use App\Models\PassengerRideBooking;
use App\Models\PassengerTransaction;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;

class PassengerTransactionSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        //
        $bookings = PassengerRideBooking::all();
        $customers = Customer::all();
        $methods = PaymentMethod::all();

        if ($bookings->isEmpty() || $customers->isEmpty() || $methods->isEmpty()) {
            $this->command->warn('⚠️ PassengerTransactionSeeder dilewati: butuh data di passenger_ride_bookings, customers, dan payment_methods.');
            return;
        }

        $statuses = ['pending', 'diterima', 'ditolak', 'credited'];

        foreach ($bookings as $booking) {
            // Ambil customer dari booking atau random
            $customer = $customers->random();
            $countToday = PassengerTransaction::whereDate('created_at', Carbon::today())->count()+1;
            $transactionCode = 'TX-' . strtoupper($booking->booking_code) . '-' . str_pad($countToday, 4, '0', STR_PAD_LEFT);

            PassengerTransaction::create([
                'passenger_ride_booking_id' => $booking->id,
                'customer_id'               => $booking->customer_id ?? $customer->id,
                'transaction_code'          => $transactionCode,
                'total_amount'              => $booking->total_price ?? rand(10000, 150000),
                'payment_method_id'         => $methods->random()->id,
                'payment_proof_img'         => null,
                'payment_status'            => $statuses[array_rand($statuses)],
                'credit_used'               => rand(0, 1) ? rand(0, 30000) : 0,
                'transaction_date'          => Carbon::now()->subDays(rand(0, 10))->setTime(rand(6, 22), rand(0, 59)),
            ]);
        }

        $this->command->info('✅ PassengerTransactionSeeder berhasil dijalankan!');
    }
}
