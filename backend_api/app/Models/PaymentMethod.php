<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class PaymentMethod extends Model
{
    //
    //
    use HasFactory;

    protected $table = 'payment_methods';

    /**
     * Kolom yang bisa diisi mass-assignment.
     */
    protected $fillable = [
        'name',
        'method',
        'code',
        'icon',
        'is_active',
    ];

    /**
     * ========================
     * RELATIONSHIPS
     * ========================
     */

    /**
     * Relasi ke PassengerTransaction
     * Satu metode pembayaran bisa digunakan di banyak transaksi penumpang.
     */
    public function passengerTransactions()
    {
        return $this->hasMany(PassengerTransaction::class, 'payment_method_id');
    }

    /**
     * Relasi ke GoodsTransaction
     * Satu metode pembayaran bisa digunakan di banyak transaksi pengiriman barang.
     */
    public function goodsTransactions()
    {
        return $this->hasMany(GoodsTransaction::class, 'payment_method_id');
    }

    /**
     * ========================
     * HELPERS
     * ========================
     */

    /**
     * Format tampilan metode pembayaran singkat.
     */
    public function getDisplayNameAttribute(): string
    {
        return "{$this->bank_name} ({$this->account_number})";
    }
}
