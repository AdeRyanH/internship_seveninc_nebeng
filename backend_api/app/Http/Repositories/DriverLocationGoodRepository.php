<?php

namespace App\Http\Repositories;

use App\Models\DriverLocationGood;

class DriverLocationGoodRepository
{
    /**
     * Find location by good ID.
     */
    public function findByGood(int $goodId): ?DriverLocationGood
    {
        return DriverLocationGood::where('good_id', $goodId)->first();
    }

    /**
     * Update or create location (STATELESS).
     */
    public function updateOrCreate(array $where, array $data): DriverLocationGood
    {
        return DriverLocationGood::updateOrCreate($where, $data);
    }

    /**
     * Deactivate tracking.
     */
    public function deactivate(int $goodId): void
    {
        DriverLocationGood::where('good_id', $goodId)
            ->update(['is_active' => false]);
    }
}
