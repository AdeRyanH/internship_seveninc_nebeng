<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PassengerPricing extends Model
{
    //
    use HasFactory;

    protected $table = 'passenger_pricings';

    protected $fillable = [
        'vehicle_type',
        'departure_terminal_id',
        'arrival_terminal_id',
        'price_per_seat',
        'commision_percentage',
    ];

    // Relationship Many To One
    public function departureTerminal() {
        return $this->belongsTo(Terminal::class, 'departure_terminal_id');
    }

    public function arrivalTerminal() {
        return $this->belongsTo(Terminal::class, 'arrival_terminal_id');
    }

    // Formating price into Rupiah
    public function getFormattedPriceAttribute(): string {
        return 'Rp'.number_format($this->price_per_seat, 0, '.', '.');
    }

    // Label kendaraaan
    public function getVehicleAttribute():string {
        return $this->vehicle_type === 'mobil' ? 'mobil' : 'motor';
    }

    // total harga setelah komisi (harga bersih untuk driver)
    public function getNetPriceAttribute(): int {
        $commission = ($this->commission_percentage / 100) * $this->price_per_seat;
        return (int) ($this->price_per_seat - $commission);
    }
}
