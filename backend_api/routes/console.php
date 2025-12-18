<?php

use Illuminate\Foundation\Inspiring;
use Illuminate\Support\Facades\Artisan;
use Illuminate\Support\Facades\Schedule;

Artisan::command('inspire', function () {
    $this->comment(Inspiring::quote());
})->purpose('Display an inspiring quote');

/*
    Cara mengaktifkan =
    php artisan schedule:work
*/
Schedule::command('midtrans:check-payment')->everyMinute();
