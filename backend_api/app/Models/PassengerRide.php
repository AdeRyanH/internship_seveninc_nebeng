<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class PassengerRide extends Model
{
    //
    use HasFactory;

    protected $table = 'passenger_rides';

    /**
     * Kolom yang bisa diisi mass-assignment
     */
    protected $fillable = [
        'driver_id',
        'vehicle_type',
        'departure_terminal_id',
        'arrival_terminal_id',
        'departure_time',
        'seats_available',
        'seats_reserved',
        'price_per_seat',
        'commission_percentage',
        'ride_status',
    ];

    /**
     * ========================
     * RELATIONSHIPS
     * ========================
     */

    /**
     * Relasi ke Driver
     * Satu ride dimiliki oleh satu driver
     */
    public function driver()
    {
        return $this->belongsTo(Driver::class, 'driver_id');
    }

    /**
     * Relasi ke Terminal Keberangkatan
     */
    public function departureTerminal()
    {
        return $this->belongsTo(Terminal::class, 'departure_terminal_id');
    }

    /**
     * Relasi ke Terminal Tujuan
     */
    public function arrivalTerminal()
    {
        return $this->belongsTo(Terminal::class, 'arrival_terminal_id');
    }

    /**
     * Relasi ke PassengerRideBooking
     * Satu ride bisa memiliki banyak booking
     */
    public function passengerRideBookings()
    {
        return $this->hasMany(PassengerRideBooking::class, 'passenger_ride_id');
    }

    /**
     * ========================
     * CASTS & CONSTANTS
     * ========================
     */

    protected $casts = [
        'departure_time' => 'datetime',
    ];

    public const STATUS_PENDING = 'pending';
    public const STATUS_TRAVELING = 'dalam_perjalanan';
    public const STATUS_DONE = 'selesai';
    public const STATUS_CANCELED = 'dibatalkan';

    public const VEHICLE_MOTOR = 'motor';
    public const VEHICLE_MOBIL = 'mobil';

    /**
     * ========================
     * HELPERS
     * ========================
     */

    /**
     * Cek apakah ride masih tersedia
     */
    public function hasAvailableSeats(): bool
    {
        return $this->seats_reserved < $this->seats_available;
    }

    /**
     * Cek apakah ride sudah selesai
     */
    public function isCompleted(): bool
    {
        return $this->ride_status === self::STATUS_DONE;
    }

    /**
     * Akses cepat untuk menghitung kursi tersisa
     */
    public function getSeatsRemainingAttribute(): int
    {
        return max(0, $this->seats_available - $this->seats_reserved);
    }

    /**
     * Hitung total pendapatan ride (belum dikurangi komisi)
     */
    public function getTotalRevenueAttribute(): int
    {
        return $this->seats_reserved * $this->price_per_seat;
    }

    /**
     * Hitung pendapatan bersih driver setelah potongan komisi
     */
    public function getDriverNetIncomeAttribute(): int
    {
        $gross = $this->getTotalRevenueAttribute();
        $commission = ($this->commission_percentage / 100) * $gross;
        return $gross - $commission;
    }
}
