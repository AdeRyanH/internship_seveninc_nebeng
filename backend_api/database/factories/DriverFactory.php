<?php

namespace Database\Factories;

use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;

class DriverFactory extends Factory
{
    public function definition(): array
    {
        return [
            // 'user_id' => User::factory()->driver(),

            // 'full_name' => fake()->name(),
            // 'telephone' => fake()->phoneNumber(),
            // 'full_address' => fake()->address(),
            // 'profile_img' => null,

            // 'balance' => fake()->numberBetween(0, 500000),
            // 'credit_score' => fake()->numberBetween(60, 100),
            // 'total_rating' => fake()->numberBetween(10, 100),
            // 'rating_count' => fake()->numberBetween(5, 50),
            // 'average_rating' => fake()->randomFloat(2, 3, 5),
            'user_id' => null, // diisi di Seeder

            'full_name' => fake()->name(),
            'telephone' => fake()->phoneNumber(),
            'full_address' => fake()->address(),
            'profile_img' => null,

            'balance' => fake()->numberBetween(0, 500000),
            'credit_score' => fake()->numberBetween(60, 100),
            'total_rating' => fake()->numberBetween(10, 100),
            'rating_count' => fake()->numberBetween(5, 50),
            'average_rating' => fake()->randomFloat(2, 3, 5),

            // ID card
            'face_img' => null,
            'face_with_id_img' => null,
            'id_card_img' => null,
            'id_card_number' => fake()->numerify('3276#########'),
            'id_card_fullname' => fake()->name(),
            'id_card_birthdate' => fake()->date(),
            'id_card_verified' => fake()->boolean(),

            // SIM
            'driver_license_number' => fake()->numerify('SIM#######'),
            'driver_license_type' => fake()->randomElement(['A', 'C']),
            'driver_license_expired' => fake()->date(),
            'driver_license_img' => null,
            'driver_license_verified' => fake()->boolean(),

            // SKCK
            'police_clearance_certificate_number' => fake()->numerify('SKCK#######'),
            'police_clearance_certificate_fullname' => fake()->name(),
            'police_clearance_certificate_expired' => fake()->date(),
            'police_clearance_certificate_img' => null,
            'police_clearance_verified' => fake()->boolean(),
        ];
    }
}
