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
        Schema::create('passenger_pricings', function (Blueprint $table) {
            $table->id();

            $table->enum('vehicle_type', ['motor', 'mobil']);

            $table->foreignId('departure_terminal_id')
                ->constrained(
                    table: 'terminals',
                    indexName: 'passenger_pricing_departure_terminal_id_fk'
                )
                ->cascadeOnUpdate()
                ->cascadeOnDelete();

            $table->foreignId('arrival_terminal_id')
                ->constrained(
                    table: 'terminals',
                    indexName: 'passenger_pricing_arrival_terminal_id_fk'
                )
                ->cascadeOnUpdate()
                ->cascadeOnDelete();

            $table->integer('price_per_seat');
            $table->integer('commision_percentage');


            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('passenger_pricings');
    }
};
