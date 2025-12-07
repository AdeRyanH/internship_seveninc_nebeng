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
        Schema::create('passenger_transactions', function (Blueprint $table) {
            $table->id();
            $table->string('transaction_code')->unique();

            $table->foreignId('passenger_ride_booking_id')
                ->constrained(
                    table: 'passenger_ride_bookings',
                    indexName: 'passenger_transactions_passenger_ride_booking_id_fk'
                )
                ->cascadeOnUpdate()
                ->cascadeOnDelete();

            $table->foreignId('customer_id')
                ->constrained(
                    table: 'customers',
                    indexName: 'passenger_transactions_customer_id_fk'
                )
                ->cascadeOnUpdate()
                ->cascadeOnDelete();

            $table->integer('total_amount');

            $table->foreignId('payment_method_id')
                ->constrained(
                    table: 'payment_methods',
                    indexName: 'passenger_transactions_payment_method_id_fk'
                )
                ->cascadeOnUpdate()
                ->restrictOnDelete();

            $table->string('payment_proof_img')->nullable();

            // $table->enum('payment_status', ['pending', 'diterima', 'ditolak', 'credited'])
            //       ->default('pending');

            // $table->integer('credit_used')->default(0);

            // ENUM disamakan dengan Model & Service: huruf awal kapital
            $table->enum('payment_status', ['pending', 'diterima', 'ditolak', 'credited'])
                  ->default('pending');

            $table->integer('credit_used')->default(0);

            // === Kolom khusus Midtrans ===
            // order_id yang dipakai di Midtrans (boleh sama dengan transaction_code)
            $table->string('midtrans_order_id')->nullable();
            $table->string('midtrans_transaction_id')->nullable(); // transaction_id dari Midtrans
            $table->string('payment_type')->nullable();            // bank_transfer, qris, dll
            $table->string('va_number')->nullable();               // nomor VA

            // Batas waktu pembayaran (buat timer di Android)
            $table->timestamp('payment_expired_at')->nullable();

            // Simpan raw response Midtrans (untuk debugging / audit)
            $table->json('payment_response_raw')->nullable();

            // Waktu transaksi tercatat di sistem
            $table->timestamp('transaction_date')->useCurrent();

            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('passenger_transactions');
    }
};
