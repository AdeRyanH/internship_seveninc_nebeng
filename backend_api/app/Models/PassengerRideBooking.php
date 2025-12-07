<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class PassengerRideBooking extends Model
{
    //
    use HasFactory;

    protected $table = 'passenger_ride_bookings';

    /**
     * Kolom yang dapat diisi mass-assignment.
     */
    protected $fillable = [
        'passenger_ride_id',
        'customer_id',
        'seats_reserved',
        'total_price',
        'status',
        'reject_status',
        'booking_code',
    ];

    // AUTO SET reject_status whenever status = "Ditolak"
    public function setStatusAttribute($value)
    {
        $this->attributes['status'] = $value;

        if ($value === 'ditolak') {
            $this->attributes['reject_status'] = 'proses';
        }
    }

    /**
     * ========================
     * RELATIONSHIPS
     * ========================
     */

    /**
     * Relasi ke PassengerRide.
     * Satu booking milik satu ride.
     */
    public function passengerRide()
    {
        return $this->belongsTo(PassengerRide::class, 'passenger_ride_id');
    }

    /**
     * Relasi ke Customer.
     * Satu booking milik satu customer.
     */
    public function customer()
    {
        return $this->belongsTo(Customer::class, 'customer_id');
    }

    /**
     * Relasi ke transaksi (PassengerTransaction)
     * Satu booking bisa punya satu transaksi.
     */
    public function passengerTransaction()
    {
        return $this->hasOne(PassengerTransaction::class, 'passenger_ride_booking_id');
    }

    /**
     * ========================
     * CASTS & CONSTANTS
     * ========================
     */

    public const STATUS_PENDING = 'pending';
    public const STATUS_ACCEPTED = 'diterima';
    public const STATUS_REJECTED = 'ditolak';

    /**
     * ========================
     * HELPERS
     * ========================
     */

    /**
     * Cek apakah booking sudah diterima.
     */
    public function isAccepted(): bool
    {
        return $this->status === self::STATUS_ACCEPTED;
    }

    /**
     * Cek apakah booking ditolak.
     */
    public function isRejected(): bool
    {
        return $this->status === self::STATUS_REJECTED;
    }

    /**
     * Hitung harga per kursi.
     */
    public function getPricePerSeatAttribute(): int
    {
        return $this->seats_reserved > 0
            ? intdiv($this->total_price, $this->seats_reserved)
            : 0;
    }

    /**
     * Format ringkasan untuk tampilan.
     */
    public function getSummaryAttribute(): string
    {
        return "Booking #{$this->id} - {$this->status} ({$this->seats_reserved} kursi)";
    }
}
