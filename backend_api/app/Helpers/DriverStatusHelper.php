<?php

namespace App\Helpers;

class DriverStatusHelper
{
    public static function getStatus($driver)
    {
        $idCard = (bool) $driver->id_card_verified;
        $sim    = (bool) $driver->driver_license_verified;
        $skck   = (bool) $driver->police_clearance_verified;

        $allVerified = $idCard && $sim && $skck;
        $anyVerified = $idCard || $sim || $skck;

        if ($allVerified) {
            return 'terverifikasi';
        }

        if ($anyVerified) {
            return 'pengajuan';
        }

        return 'terblokir';
    }
}


