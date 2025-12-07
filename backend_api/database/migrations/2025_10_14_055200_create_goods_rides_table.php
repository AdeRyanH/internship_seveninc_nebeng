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
        Schema::create('goods_rides', function (Blueprint $table) {
            $table->id();

            // Relasi ke driver
            $table->foreignId('driver_id')
                ->constrained(
                    table: 'drivers',
                    indexName: 'goods_rides_driver_id_fk'
                )
                ->cascadeOnUpdate()
                ->cascadeOnDelete();

            // Jenis transportasi (Sendiri / Umum)
            $table->enum('transport_type', ['sendiri', 'umum']);

            // Subtipe terminal publik (Terminal Bis / Stasiun Kereta / Bandara / Pelabuhan)
            $table->enum('public_terminal_subtype', ['terminal_bis', 'stasiun_kereta', 'bandara', 'pelabuhan']);

            // Terminal keberangkatan dan tujuan
            $table->foreignId('departure_terminal_id')
                ->constrained(
                    table: 'terminals',
                    indexName: 'goods_rides_departure_terminal_id_fk'
                )
                ->cascadeOnUpdate()
                ->restrictOnDelete();

            $table->foreignId('arrival_terminal_id')
                ->constrained(
                    table: 'terminals',
                    indexName: 'goods_rides_arrival_terminal_id_fk'
                )
                ->cascadeOnUpdate()
                ->restrictOnDelete();

            $table->timestamp('departure_time');

            // Kapasitas berat maksimum dan berat yang sudah terpakai
            $table->integer('max_weight');
            $table->integer('weight_reserved')->default(0);

            // Harga per kilogram barang
            $table->integer('price_per_kg');

            // Persentase komisi untuk platform
            $table->integer('commission_percentage')->default(0);

            // Status perjalanan
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
        Schema::dropIfExists('goods_rides');
    }
};
