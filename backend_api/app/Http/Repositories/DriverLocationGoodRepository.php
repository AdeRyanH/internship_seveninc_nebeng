<?php

namespace App\Http\Repositories;

use App\Models\DriverLocationGood;

class DriverLocationGoodRepository
{
    // protected $model;

    // /**
    //  * Inject DriverLocation model instance.
    //  */
    // public function __construct(DriverLocationGood $model)
    // {
    //     $this->model = $model;
    // }

    // /**
    //  * Find active location by good ID.
    //  */
    // public function findByGood(int $goodId): ?DriverLocationGood
    // {
    //     return $this->model
    //         ->where('good_id', $goodId)
    //         ->first();
    // }

    // /**
    //  * Update or create location record.
    //  */
    // public function updateOrCreate(array $where, array $data): DriverLocationGood
    // {
    //     return $this->model->updateOrCreate($where, $data);
    // }

    // /**
    //  * Deactivate tracking for good.
    //  */
    // public function deactivate(int $goodId): void
    // {
    //     $this->model
    //         ->where('good_id', $goodId)
    //         ->update(['is_active' => false]);
    // }
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
