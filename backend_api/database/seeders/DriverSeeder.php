<?php

namespace Database\Seeders;

use Carbon\Carbon;
use App\Models\User;
use App\Models\Driver;
use Illuminate\Database\Seeder;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;

class DriverSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        //
        $users = User::where('user_type', 'driver')->get();

        if ($users->isEmpty()) {
            $this->command->warn('⚠️ DriverSeeder dilewati: belum ada user dengan user_type = driver.');
            return;
        }

        $drivers = [
            [
                'full_name'   => 'Budi Santoso',
                'telephone'   => '081234567890',
                'full_address'=> 'Jl. Kaliurang No. 45, Sleman, Yogyakarta',
            ],
            [
                'full_name'   => 'Andi Pratama',
                'telephone'   => '081298765432',
                'full_address'=> 'Jl. Ringroad Selatan No. 21, Bantul, Yogyakarta',
            ],
            [
                'full_name'   => 'Siti Rohani',
                'telephone'   => '082112223333',
                'full_address'=> 'Jl. Malioboro No. 7, Yogyakarta',
            ],
            [
                'full_name'   => 'Dewi Lestari',
                'telephone'   => '081345678901',
                'full_address'=> 'Jl. Magelang KM 5, Sleman, Yogyakarta',
            ],
            [
                'full_name'   => 'Agus Wijaya',
                'telephone'   => '081223344556',
                'full_address'=> 'Jl. Wates No. 12, Bantul, Yogyakarta',
            ],
        ];

        foreach ($drivers as $index => $data) {
            $user = $users[$index % $users->count()]; // rotasi user driver

            $totalRating = rand(10, 50); // total skor akumulatif
            $ratingCount = rand(3, 10);  // jumlah ulasan

            $averageRating = round($totalRating / $ratingCount, 2);
            Driver::updateOrCreate(
                ['user_id' => $user->id],
                [
                    'user_id'       => $user->id,
                    'full_name'     => $data['full_name'],
                    'telephone'     => $data['telephone'],
                    'full_address'  => $data['full_address'],
                    'profile_img'   => null,
                    'balance'       => rand(100000, 1000000),
                    'credit_score'  => rand(70, 100),

                    // rating
                    'total_rating'  => $totalRating,
                    'rating_count'  => $ratingCount,
                    'average_rating'=> $averageRating,


                    // ID Card
                    'id_card_number'          => 'ID' . rand(10000000, 99999999),
                    'id_card_fullname'        => $data['full_name'],
                    'id_card_birthdate' => Carbon::today()
                        ->subYears(rand(20, 50))
                        ->subDays(rand(0, 365))
                        ->toDateString(),
                    'id_card_verified'        => rand(0, 1),
                    'id_card_rejected_reason' => null,
                    'id_card_img'             => null,
                    'face_img'                => null,
                    'face_with_id_img'        => null,

                    // SIM (Driver License)
                    'driver_license_number'   => 'SIM' . rand(100000, 999999),
                    'driver_license_type'     => rand(0, 1) ? 'A' : 'C',
                    'driver_license_expired'  => Carbon::now()->addYears(rand(2, 5)),
                    'driver_license_verified' => rand(0, 1),
                    'driver_license_img'      => null,
                    'driver_license_rejected_reason' => null,

                    // SKCK (Police Clearance)
                    'police_clearance_certificate_number'   => 'SKCK' . rand(10000, 99999),
                    'police_clearance_certificate_fullname' => $data['full_name'],
                    'police_clearance_certificate_expired'  => Carbon::now()->addYears(rand(1, 3)),
                    'police_clearance_certificate_img'      => null,
                    'police_clearance_verified'             => rand(0, 1),
                    'police_clearance_rejected_reason'      => null,
                ]
            );
        }

        $this->command->info('✅ DriverSeeder berhasil dijalankan!');
    }
}
