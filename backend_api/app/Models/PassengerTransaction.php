<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class PassengerTransaction extends Model
{
    //
    use HasFactory;

    protected $table = 'passenger_transactions';

    /**
     * Kolom yang bisa diisi secara mass-assignment
     */
    protected $fillable = [
        'passenger_ride_booking_id',
        'customer_id',
        'total_amount',
        // diubah dari payment_method ke payment_method_id
        'payment_method_id',
        'payment_proof_img',
        'payment_status',
        'credit_used',
        'transaction_date',
        'transaction_code',

        'midtrans_order_id',
        'midtrans_transaction_id',
        'payment_type',
        'va_number',
        'payment_expired_at',
        'payment_response_raw',
    ];

    /**
     * ========================
     * RELATIONSHIPS
     * ========================
     */

    /**
     * Relasi ke tabel PassengerRideBooking
     * One Transaction belongs to one PassengerRideBooking
     */
    public function passengerRideBooking()
    {
        return $this->belongsTo(PassengerRideBooking::class, 'passenger_ride_booking_id');
    }

    /**
     * Relasi ke tabel Customer
     * One Transaction belongs to one Customer
     */
    public function customer()
    {
        return $this->belongsTo(Customer::class, 'customer_id');
    }

    /**
     * Relasi ke tabel PaymentMethod
     * One Transaction uses one PaymentMethod
     */
    public function paymentMethod()
    {
        return $this->belongsTo(PaymentMethod::class, 'payment_method_id');
    }

    /**
     * ========================
     * CASTS & CONSTANTS
     * ========================
     */

    /**
     * Ubah kolom timestamp ke instance Carbon
     */
    // protected $casts = [
    //     'transaction_date' => 'datetime',
    //     'credit_used' => 'integer',
    // ];
     protected $casts = [
        'transaction_date'    => 'datetime',
        'payment_expired_at'  => 'datetime',
        'credit_used'         => 'integer',
        'payment_response_raw'=> 'array',
    ];

    /**
     * Status pembayaran yang mungkin (sesuai ENUM di migration)
     */
    public const STATUS_PENDING   = 'pending';
    public const STATUS_DITERIMA  = 'diterima';
    public const STATUS_DITOLAK   = 'ditolak';
    public const STATUS_CREDITED  = 'credited';

    /**
     * Helper untuk memeriksa status
     */
    public function isPending(): bool
    {
        return $this->payment_status === self::STATUS_PENDING;
    }

    public function isApproved(): bool
    {
        return $this->payment_status === self::STATUS_DITERIMA;
    }

    public function isRejected(): bool
    {
        return $this->payment_status === self::STATUS_DITOLAK;
    }

    public function isCredited(): bool
    {
        return $this->payment_status === self::STATUS_CREDITED;
    }

    /**
     * Accessor contoh – format tanggal transaksi
     */
    public function getFormattedDateAttribute(): string
    {
        return $this->transaction_date
            ? $this->transaction_date->format('d M Y, H:i')
            : '-';
    }
}
