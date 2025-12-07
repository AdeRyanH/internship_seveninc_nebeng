<?php

namespace Database\Factories;

use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;

class CustomerFactory extends Factory
{
    // public function definition(): array
    // {
    //     return [
    //         'user_id' => User::factory()->customer(),
    //         'full_name' => fake()->name(),
    //         'telephone' => fake()->phoneNumber(),
    //         'full_address' => fake()->address(),
    //         'profile_img' => null,
    //         'verified' => fake()->boolean(30),
    //         'face_img' => null,
    //         'face_with_id_img' => null,
    //         'id_card_img' => null,
    //         'id_card_number' => fake()->numerify('3276#########'),
    //         'id_card_fullname' => fake()->name(),
    //         'id_card_birthdate' => fake()->date(),
    //         'credit_amount' => fake()->numberBetween(10000, 300000),
    //     ];
    // }
    public function definition(): array
    {
        return [
            'user_id' => null, // diisi di Seeder
            'full_name' => fake()->name(),
            'telephone' => fake()->phoneNumber(),
            'full_address' => fake()->address(),
            'profile_img' => null,
            'verified' => fake()->boolean(30),
            'face_img' => null,
            'face_with_id_img' => null,
            'id_card_img' => null,
            'id_card_number' => fake()->numerify('3276#########'),
            'id_card_fullname' => fake()->name(),
            'id_card_birthdate' => fake()->date(),
            'credit_amount' => fake()->numberBetween(10000, 300000),
        ];
    }
}
