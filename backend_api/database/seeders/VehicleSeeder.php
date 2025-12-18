<?php

namespace Database\Seeders;

use Carbon\Carbon;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class VehicleSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        // Ambil semua driver_id dari tabel drivers
        $drivers = DB::table('drivers')->pluck('id')->toArray();

        if (empty($drivers)) {
            $this->command->warn('⚠️ Tidak ada data driver di tabel drivers. Jalankan DriverSeeder terlebih dahulu.');
            return;
        }

        $this->command->info('🚗 Membuat 100 data kendaraan otomatis...');

        $vehicleNames = [
            'Honda Beat', 'Honda Vario', 'Yamaha NMAX', 'Yamaha Aerox',
            'Honda Scoopy', 'Yamaha Mio', 'Suzuki Nex', 'Honda PCX',
            'Honda Supra X', 'Yamaha Jupiter MX'
        ];

        $vehicleColors = [
            'Hitam', 'Putih', 'Merah', 'Biru', 'Silver', 'Abu-Abu', 'Kuning'
        ];

        $vehicleTypes = ['motor', 'mobil'];

        $data = [];
        $now = Carbon::now();

        for ($i = 0; $i < 100; $i++) {
            $vehicleName = $vehicleNames[array_rand($vehicleNames)];
            $color = $vehicleColors[array_rand($vehicleColors)];
            $type = $vehicleTypes[array_rand($vehicleTypes)];

            // Random nomor polisi
            $registrationNumber =
                strtoupper(substr(str_shuffle('ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 0, 1)) .
                rand(1000, 9999) .
                strtoupper(substr(str_shuffle('ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 0, 2));

            $data[] = [
                'driver_id'             => $drivers[array_rand($drivers)],
                'registration_number'   => $registrationNumber,
                'registration_year'     => rand(2015, 2024),
                'registration_expired'  => Carbon::now()->addYears(rand(1, 5)),
                'registration_img'      => "vehicle_docs/{$registrationNumber}_stnk.jpg",
                'vehicle_name'          => $vehicleName,
                'vehicle_color'         => $color,
                'vehicle_type'          => $type,
                'vehicle_img'           => "vehicles/{$vehicleName}_{$color}.jpg",
                'vehicle_verified'      => rand(0, 1),
                'vehicle_rejected_reason' => null,
                'created_at'            => $now,
                'updated_at'            => $now,
            ];
        }

        DB::table('vehicles')->insert($data);

        $this->command->info('✅ 100 data vehicle berhasil dibuat!');
    }
}
