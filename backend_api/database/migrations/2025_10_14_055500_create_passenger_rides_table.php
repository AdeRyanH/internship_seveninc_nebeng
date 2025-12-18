<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('passenger_rides', function (Blueprint $table) {
            $table->id();

            // Relasi ke driver
            $table->foreignId('driver_id')
                ->constrained(
                    table: 'drivers',
                    indexName: 'passenger_rides_driver_id_fk'
                )
                ->cascadeOnUpdate()
                ->cascadeOnDelete();

            // Jenis kendaraan: Motor / Mobil
            $table->enum('vehicle_type', ['motor', 'mobil']);

            // Relasi terminal keberangkatan dan tujuan
            $table->foreignId('departure_terminal_id')
                ->constrained(
                    table: 'terminals',
                    indexName: 'passenger_rides_departure_terminal_id_fk'
                )
                ->cascadeOnUpdate()
                ->restrictOnDelete();

            $table->foreignId('arrival_terminal_id')
                ->constrained(
                    table: 'terminals',
                    indexName: 'passenger_rides_arrival_terminal_id_fk'
                )
                ->cascadeOnUpdate()
                ->restrictOnDelete();

            $table->timestamp('departure_time');
            $table->integer('seats_available');
            $table->integer('seats_reserved')->default(0);
            $table->integer('price_per_seat');
            $table->integer('commission_percentage')->default(0);

            // ENUM status perjalanan
            $table->enum('ride_status', ['pending', 'dalam_perjalanan', 'selesai', 'dibatalkan'])
                  ->default('pending');

            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('passenger_rides');
    }
};
