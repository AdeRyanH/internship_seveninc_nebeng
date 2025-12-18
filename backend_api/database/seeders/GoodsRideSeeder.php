<?php

namespace Database\Seeders;

use Carbon\Carbon;
use App\Models\Driver;
use App\Models\Terminal;
use App\Models\GoodsRide;
use Illuminate\Database\Seeder;

class GoodsRideSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $drivers   = Driver::all();
        $terminals = Terminal::all();

        if ($drivers->isEmpty() || $terminals->count() < 2) {
            $this->command->warn('⚠️ GoodsRideSeeder dilewati: butuh minimal 1 driver dan 2 terminal.');
            return;
        }

        $transportTypes = ['sendiri', 'umum'];
        $subtypes       = ['terminal_bis', 'stasiun_kereta', 'bandara', 'pelabuhan'];
        $statuses       = ['pending', 'dalam_perjalanan', 'selesai', 'dibatalkan'];

        foreach ($drivers as $driver) {
            $ridesToGenerate = rand(2, 5);

            for ($i = 0; $i < $ridesToGenerate; $i++) {
                // Pilih terminal keberangkatan & tujuan berbeda
                $departure = $terminals->random();
                do {
                    $arrival = $terminals->random();
                } while ($arrival->id === $departure->id);

                $maxWeight  = rand(200, 1000); // kg
                $reserved   = rand(0, (int)($maxWeight / 2));
                $pricePerKg = rand(2000, 10000);
                $type       = $transportTypes[array_rand($transportTypes)];

                // Jika "Umum", isi public_terminal_subtype, kalau "Sendiri" pilih random tapi optional
                $subtype = $type === 'umum'
                    ? $subtypes[array_rand($subtypes)]
                    : $subtypes[array_rand($subtypes)];

                GoodsRide::create([
                    'driver_id'              => $driver->id,
                    'transport_type'         => $type,
                    'public_terminal_subtype'=> $subtype,
                    'departure_terminal_id'  => $departure->id,
                    'arrival_terminal_id'    => $arrival->id,
                    'departure_time'         => Carbon::now()
                                                    ->addDays(rand(1, 10))
                                                    ->setTime(rand(5, 20), 0),
                    'max_weight'             => $maxWeight,
                    'weight_reserved'        => $reserved,
                    'price_per_kg'           => $pricePerKg,
                    'commission_percentage'  => rand(5, 15),
                    'ride_status'            => $statuses[array_rand($statuses)],
                ]);
            }
        }

        $this->command->info('✅ GoodsRideSeeder berhasil dijalankan!');
    }
}
