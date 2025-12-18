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
        Schema::create('passenger_ride_bookings', function (Blueprint $table) {
            $table->id();
            $table->string('booking_code')->unique();
            // Relasi ke passenger_rides
            $table->foreignId('passenger_ride_id')
                ->constrained(
                    table: 'passenger_rides',
                    indexName: 'passenger_ride_bookings_passenger_ride_id_fk'
                )
                ->cascadeOnUpdate()
                ->cascadeOnDelete();

            // Relasi ke customers
            $table->foreignId('customer_id')
                ->constrained(
                    table: 'customers',
                    indexName: 'passenger_ride_bookings_customer_id_fk'
                )
                ->cascadeOnUpdate()
                ->cascadeOnDelete();

            // Jumlah kursi yang dipesan
            $table->integer('seats_reserved')->nullable();

            // Total harga dari semua kursi
            $table->integer('total_price')->nullable();

            // Status booking (enum)
            $table->enum('status', ['pending', 'diterima', 'ditolak'])
                  ->default('pending');
            $table->enum('reject_status', ['proses', 'selesai', 'batal'])
                  ->default(null)->nullable();

            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('passenger_ride_bookings');
    }
};
