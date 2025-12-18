<?php

namespace App\Http\Services;

use InvalidArgumentException;
use App\Http\Services\GoodsRideService;
use App\Http\Services\PassengerRideService;
use Illuminate\Pagination\LengthAwarePaginator;

class RideService{

    protected $passengerRideService;
    protected $goodsRideService;

    public function __construct(
        PassengerRideService $passengerService,
        GoodsRideService $goodservice
    ) {
        $this->passengerRideService = $passengerService;
        $this->goodsRideService = $goodservice;
    }

    public function listAllRides($perPage = 10) {
            $passengerRides = $this->passengerRideService->listRides()->map(function ($d) {
                $d->ride_type = 'Pasengger';
                return $d;
            });
            $goodsRides = $this->goodsRideService->listGoodsRides()->map(function($d){
                $d->ride_type = 'Goods';
                return $d;
            });

            $merged = collect()
                ->merge($passengerRides)
                ->merge($goodsRides)
                ->sortByDesc('created_at')
                ->values();

            $page = request('page', 1);
            $total = $merged->count();
            $paginated = new LengthAwarePaginator(
                $merged->slice(($page - 1) * $perPage, $perPage)->values(),
                $total,
                $perPage,
                $page,
            [
                        'path' => request()->url(),
                        'query' => request()->query()
                    ]
            );

            return $paginated;
    }

    public function getDetailRide(string $type, $id){
        if($type === 'passenger'){
            return $this->passengerRideService->getRide($id);
        } else if ($type === 'goods'){
            return $this->goodsRideService->getGoodsRide($id);
        }

        throw new \InvalidArgumentException('Tipe ride tidak valid');
    }

    public function listByDriver($driverId){
        return[
            'passenger_rides' => $this->passengerRideService->listByDriver($driverId),
            'goods_rides' => $this->goodsRideService->listByDriver($driverId)
        ];
    }
}
