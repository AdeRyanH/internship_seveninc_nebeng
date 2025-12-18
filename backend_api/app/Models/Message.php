<?php

namespace App\Models;

use Chatify\Traits\UUID;
use Illuminate\Database\Eloquent\Model;

class ChMessage extends Model
{
    use UUID;

    protected $table = 'ch_message'; // nama tabel di DB
    protected $fillable = [
        'from_id',
        'to_id',
        'body',
        'type'
    ];
}
