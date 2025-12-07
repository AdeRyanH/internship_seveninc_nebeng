<?php

namespace Database\Seeders;

use Carbon\Carbon;
use App\Models\Driver;
use App\Models\Terminal;
use App\Models\PassengerRide;
use Illuminate\Database\Seeder;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;

class PassengerRideSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        //
        // $drivers   = Driver::all();
        // $terminals = Terminal::all();

        // if ($drivers->isEmpty() || $terminals->count() < 2) {
        //     $this->command->warn('⚠️ PassengerRideSeeder dilewati: butuh minimal 1 driver dan 2 terminal.');
        //     return;
        // }

        // $vehicleTypes = ['Motor', 'Mobil'];
        // $statuses = ['Pending', 'Dalam Perjalanan', 'Selesai', 'Dibatalkan'];

        // foreach ($drivers as $driver) {
        //     $rideCount = rand(2, 5);

        //     for ($i = 0; $i < $rideCount; $i++) {
        //         // Pilih terminal keberangkatan & tujuan yang berbeda
        //         $departure = $terminals->random();
        //         do {
        //             $arrival = $terminals->random();
        //         } while ($arrival->id === $departure->id);

        //         $seatsAvailable = rand(2, 6);
        //         $seatsReserved  = rand(0, $seatsAvailable);
        //         $pricePerSeat   = rand(10000, 50000);

        //         PassengerRide::create([
        //             'driver_id'             => $driver->id,
        //             'vehicle_type'          => $vehicleTypes[array_rand($vehicleTypes)],
        //             'departure_terminal_id' => $departure->id,
        //             'arrival_terminal_id'   => $arrival->id,
        //             'departure_time'        => Carbon::now()->addDays(rand(-3, 10))->setTime(rand(5, 20), 0),
        //             'seats_available'       => $seatsAvailable,
        //             'seats_reserved'        => $seatsReserved,
        //             'price_per_seat'        => $pricePerSeat,
        //             'commission_percentage' => rand(5, 15),
        //             'ride_status'           => $statuses[array_rand($statuses)],
        //         ]);
        //     }
        // }

        // $this->command->info('✅ PassengerRideSeeder berhasil dijalankan!');
        $drivers   = Driver::all();
        $terminals = Terminal::all();

        if ($drivers->isEmpty() || $terminals->count() < 2) {
            $this->command->warn('⚠️ PassengerRideSeeder dilewati: butuh minimal 1 driver dan 2 terminal.');
            return;
        }

        $vehicleTypes = ['motor', 'mobil'];
        $statuses = ['pending', 'dalam_perjalanan', 'selesai', 'dibatalkan'];

        foreach ($drivers as $driver) {
            $rideCount = rand(2, 4);

            for ($i = 0; $i < $rideCount; $i++) {
                // pilih terminal keberangkatan & tujuan yang berbeda
                $departure = $terminals->random();
                do {
                    $arrival = $terminals->random();
                } while ($arrival->id === $departure->id);

                $seatsAvailable = rand(3, 6);
                $seatsReserved  = rand(0, $seatsAvailable - 1);
                $pricePerSeat   = rand(10000, 50000);

                PassengerRide::create([
                    'driver_id'             => $driver->id,
                    'vehicle_type'          => $vehicleTypes[array_rand($vehicleTypes)],
                    'departure_terminal_id' => $departure->id,
                    'arrival_terminal_id'   => $arrival->id,
                    'departure_time'        => Carbon::now()
                                                     ->addDays(rand(1, 7))
                                                     ->setTime(rand(5, 20), 0),
                    'seats_available'       => $seatsAvailable,
                    'seats_reserved'        => $seatsReserved,
                    'price_per_seat'        => $pricePerSeat,
                    'commission_percentage' => rand(5, 15),
                    'ride_status'           => $statuses[array_rand($statuses)],
                ]);
            }
        }

        $this->command->info('✅ PassengerRideSeeder berhasil dijalankan! Data rides penumpang berhasil ditambahkan.');
    }
}
