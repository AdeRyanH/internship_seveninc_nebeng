<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class DriverLocation extends Model
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
}
