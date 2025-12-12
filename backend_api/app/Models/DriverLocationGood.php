<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class DriverLocationGood extends Model
{
    //
    protected $fillable = [
        'ride_id',
        'driver_id',
        'latitude',
        'longitude',
        'last_seen_at',
        'is_active',
    ];

    protected $casts = [
        'latitude' => 'float',
        'longitude' => 'float',
        'is_active' => 'boolean',
        'last_seen_at' => 'datetime',
    ];
}
