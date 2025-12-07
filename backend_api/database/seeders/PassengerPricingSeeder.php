<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\PassengerPricing;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;

class PassengerPricingSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        // //
        // $this->command->info('⚠️ PassengerPricingSeeder dijalankan!');

        // PassengerPricing::insert([
        //     [
        //         'vehicle_type'          => 'motor',
        //         'departure_terminal_id' => 1,
        //         'arrival_terminal_id'   => 2,
        //         'price_per_seat'        => 25000,
        //         'commision_percentage'  => 10,
        //     ],
        //     [
        //         'vehicle_type'          => 'mobil',
        //         'departure_terminal_id' => 1,
        //         'arrival_terminal_id'   => 3,
        //         'price_per_seat'        => 50000,
        //         'commision_percentage'  => 12,
        //     ],
        //     [
        //         'vehicle_type'          => 'mobil',
        //         'departure_terminal_id' => 2,
        //         'arrival_terminal_id'   => 3,
        //         'price_per_seat'        => 70000,
        //         'commision_percentage'  => 15,
        //     ],
        // ]);

        // $this->command->info('✅ PassengerRideBookingSeeder berhasil dijalankan!');

        $this->command->info('🚀 Generating automatic passenger pricing...');

        $terminalIds = [1, 2, 3, 4, 5];

        $motorStartPrice = 25000;
        $mobilStartPrice = 50000;

        $motorIncrement = 1000;
        $mobilIncrement = 1000;

        $currentMotorPrice = $motorStartPrice;
        $currentMobilPrice = $mobilStartPrice;

        $pairPrices = []; // Untuk menyimpan harga per-pair agar A→B dan B→A sama

        foreach ($terminalIds as $i) {
            foreach ($terminalIds as $j) {

                if ($i === $j) continue; // Skip sama-sama

                // Buat key agar pasangan 1-2 sama dengan 2-1
                $pairKey = ($i < $j) ? "{$i}-{$j}" : "{$j}-{$i}";

                // Jika pair belum pernah dibuat → generate harga baru
                if (!isset($pairPrices[$pairKey])) {
                    $pairPrices[$pairKey] = [
                        'motor' => $currentMotorPrice,
                        'mobil' => $currentMobilPrice,
                    ];

                    // increment harga untuk pasangan berikutnya
                    $currentMotorPrice += $motorIncrement;
                    $currentMobilPrice += $mobilIncrement;
                }

                // Simpan dua jenis kendaraan
                PassengerPricing::create([
                    'vehicle_type'          => 'motor',
                    'departure_terminal_id' => $i,
                    'arrival_terminal_id'   => $j,
                    'price_per_seat'        => $pairPrices[$pairKey]['motor'],
                    'commision_percentage'  => 10,
                ]);

                PassengerPricing::create([
                    'vehicle_type'          => 'mobil',
                    'departure_terminal_id' => $i,
                    'arrival_terminal_id'   => $j,
                    'price_per_seat'        => $pairPrices[$pairKey]['mobil'],
                    'commision_percentage'  => 15,
                ]);
            }
        }

        $this->command->info("✅ Selesai! Total data masuk: " . PassengerPricing::count());
    }
}
