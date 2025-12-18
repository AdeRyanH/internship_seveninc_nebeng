<?php

namespace Database\Seeders;

use Carbon\Carbon;
use App\Models\Customer;
use App\Models\GoodsRide;
use Illuminate\Database\Seeder;
use App\Models\GoodsRideBooking;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;

class GoodsRideBookingSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        //
        $rides = GoodsRide::all();
        $customers = Customer::all();

        if ($rides->isEmpty() || $customers->isEmpty()) {
            $this->command->warn('⚠️ GoodsRideBookingSeeder dilewati: butuh data di goods_rides dan customers.');
            return;
        }

        $statuses = ['pending', 'diterima', 'ditolak'];
        $descriptions = [
            'Paket elektronik (laptop, charger, dan aksesoris)',
            'Bahan makanan segar dan minuman ringan',
            'Barang pecah belah - mohon hati-hati',
            'Dokumen penting, jangan dilipat',
            'Pakaian dan perlengkapan pribadi',
            'Peralatan bengkel ringan',
        ];

        foreach ($rides as $ride) {
            // Maksimal 3 booking per goods_ride
            $bookingCount = rand(1, 3);

            for ($i = 0; $i < $bookingCount; $i++) {
                $customer = $customers->random();
                $itemWeight = rand(5, 100); // kg
                $pricePerKg = $ride->price_per_kg ?? rand(2000, 10000);
                $totalPrice = $itemWeight * $pricePerKg;
                $status = $statuses[array_rand($statuses)];

                // Jika ditolak → reject_status = "proses"
                $rejectStatus = $status === 'ditolak' ? 'proses' : null;

                // Generate nomor booking
                $today = Carbon::today()->format('Ymd');
                $countToday = GoodsRideBooking::whereDate('created_at', Carbon::today())->count() + 1;
                $bookingNumber = 'G-' . $today . '-' . str_pad($countToday, 4, '0', STR_PAD_LEFT);

                GoodsRideBooking::create([
                    'goods_ride_id'   => $ride->id,
                    'customer_id'     => $customer->id,
                    'item_weight'     => $itemWeight,
                    'item_description'=> $descriptions[array_rand($descriptions)],
                    'item_img'        => null,
                    'total_price'     => $totalPrice,
                    'status'          => $status,
                    'reject_status'   => $rejectStatus,
                    'booking_code'    => $bookingNumber,
                ]);

                // Jika booking diterima, tambahkan berat ke ride
                if ($status === 'diterima') {
                    $ride->increment('weight_reserved', $itemWeight);
                }
            }
        }

        $this->command->info('✅ GoodsRideBookingSeeder berhasil dijalankan!');
    }
}
