<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class GoodsPricing extends Model
{
    //
    use HasFactory;

    protected $table = 'goods_pricings';

    protected $fillable = [
        'transport_type',
        'departure_terminal_id',
        'arrival_terminal_id',
        'price_per_kg',
        'commission_percentage',
    ];

    // relationship Many To One
    public function departureTerminal() {
        return $this->belongsTo(Terminal::class, 'departure_terminal_id');
    }

    public function arrivalTerminal() {
        return $this->belongsTo(Terminal::class, 'arrival_terminal_id');
    }

    // Format harga per kg dalam format rupiah
    public function getFormattedPriceAttribute(): string {
        return 'Rp'.number_format($this->price_per_kg, 0 ,'.', '.');
    }

    // Label transportasi umum / sendiri
    public function getFormattedLabelAttribute():string {
        return $this->transport_type === 'umum' ? 'umum' : 'sendiri';
    }

    // Harga bersih yang diterima driver (setelah komisi sistem)
    public function getNetPriceAttribute(): int {
        $commission = ($this->commission_percentage / 100) * $this->price_per_kg;
        return (int) ($this->price_per_kg - $commission);
    }
}
