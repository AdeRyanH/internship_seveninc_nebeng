<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class Vehicle extends Model
{
    //
    use HasFactory;

    protected $table = 'vehicles';

    /**
     * Kolom yang bisa diisi mass assignment.
     */
    protected $fillable = [
        'driver_id',
        'registration_number',
        'registration_year',
        'registration_expired',
        'registration_img',
        'vehicle_name',
        'vehicle_color',
        'vehicle_type',
        'vehicle_img',
        'vehicle_verified',
        'vehicle_rejected_reason',
    ];

    /**
     * ========================
     * RELATIONSHIPS
     * ========================
     */

    /**
     * Satu kendaraan dimiliki oleh satu driver.
     */
    public function driver()
    {
        return $this->belongsTo(Driver::class, 'driver_id');
    }

    /**
     * ========================
     * CASTS & CONSTANTS
     * ========================
     */
    protected $casts = [
        'vehicle_verified' => 'boolean',
    ];

    /**
     * Enum kendaraan (konstanta)
     */
    public const TYPE_MOTOR = 'motor';
    public const TYPE_MOBIL = 'mobil';

    /**
     * ========================
     * HELPERS
     * ========================
     */

    /**
     * Mengecek apakah kendaraan sudah diverifikasi.
     */
    public function isVerified(): bool
    {
        return $this->vehicle_verified === true;
    }

    /**
     * Mengecek apakah kendaraan ditolak.
     */
    public function isRejected(): bool
    {
        return !is_null($this->vehicle_rejected_reason);
    }

    /**
     * Accessor singkat untuk label status verifikasi.
     */
    public function getVerificationStatusAttribute(): string
    {
        if ($this->isVerified()) {
            return 'Terverifikasi';
        }

        return $this->isRejected() ? 'ditolak' : 'Menunggu Verifikasi';
    }
}
