<?php

namespace Database\Seeders;

use Carbon\Carbon;
use App\Models\Customer;
use App\Models\PassengerRide;
use Illuminate\Database\Seeder;
use App\Models\PassengerRideBooking;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;

class PassengerRideBookingSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        //
        $rides = PassengerRide::all();
        $customers = Customer::all();

        if ($rides->isEmpty() || $customers->isEmpty()) {
            $this->command->warn('⚠️ PassengerRideBookingSeeder dilewati: butuh data di passenger_rides dan customers.');
            return;
        }

        $statuses = ['pending', 'diterima', 'ditolak'];

        foreach ($rides as $ride) {
            // Maksimal 3 booking per ride
            $bookingCount = rand(1, 3);

            for ($i = 0; $i < $bookingCount; $i++) {
                $customer = $customers->random();

                // Tentukan jumlah kursi dipesan (jangan lebih dari seats_available)
                $seatsReserved = rand(1, max(1, $ride->seats_available - $ride->seats_reserved));
                $totalPrice = $seatsReserved * $ride->price_per_seat;
                $status = $statuses[array_rand($statuses)];

                // Jika ditolak → reject_status = "proses"
                $rejectStatus = $status === 'ditolak' ? 'proses' : null;

                // Generate nomor booking
                $today = Carbon::today()->format('Ymd');
                $countToday = PassengerRideBooking::whereDate('created_at', Carbon::today())->count() + 1;
                $bookingNumber = 'P-' . $today . '-' . str_pad($countToday, 4, '0', STR_PAD_LEFT);

                PassengerRideBooking::create([
                    'passenger_ride_id' => $ride->id,
                    'customer_id'       => $customer->id,
                    'seats_reserved'    => $seatsReserved,
                    'total_price'       => $totalPrice,
                    'status'            => $status,
                    'reject_status'     => $rejectStatus,
                    'booking_code'      => $bookingNumber,
                ]);

                // Jika booking diterima, tambahkan seats_reserved di ride
                if ($status === 'diterima') {
                    $ride->increment('seats_reserved', $seatsReserved);
                }
            }
        }

        $this->command->info('✅ PassengerRideBookingSeeder berhasil dijalankan!');
    }
}
