<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class DriverWithdrawal extends Model
{
    //
    use HasFactory;

    protected $table = 'driver_withdrawals';

    /**
     * Kolom yang bisa diisi mass-assignment.
     */
    protected $fillable = [
        'driver_id',
        'withdrawal_amount',
        'bank_name',
        'account_name',
        'account_number',
        'status',
        'rejected_reason',
    ];

    /**
     * ========================
     * RELATIONSHIPS
     * ========================
     */

    /**
     * Relasi ke Driver.
     * Satu withdrawal dimiliki oleh satu driver.
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
        'withdrawal_amount' => 'integer',
        'created_at' => 'datetime',
        'updated_at' => 'datetime',
    ];

    public const STATUS_PENDING = 'pending';
    public const STATUS_ACCEPTED = 'diterima';
    public const STATUS_REJECTED = 'ditolak';

    /**
     * ========================
     * HELPERS
     * ========================
     */

    /**
     * Cek apakah penarikan masih pending.
     */
    public function isPending(): bool
    {
        return $this->status === self::STATUS_PENDING;
    }

    /**
     * Cek apakah penarikan diterima.
     */
    public function isAccepted(): bool
    {
        return $this->status === self::STATUS_ACCEPTED;
    }

    /**
     * Cek apakah penarikan ditolak.
     */
    public function isRejected(): bool
    {
        return $this->status === self::STATUS_REJECTED;
    }

    /**
     * Format jumlah penarikan ke rupiah.
     */
    public function getFormattedAmountAttribute(): string
    {
        return 'Rp' . number_format($this->withdrawal_amount, 0, ',', '.');
    }

    /**
     * Ringkasan singkat untuk tampilan.
     */
    public function getSummaryAttribute(): string
    {
        return "Withdrawal #{$this->id} | {$this->formatted_amount} ({$this->status})";
    }
}
