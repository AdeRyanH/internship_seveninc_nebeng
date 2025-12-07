<?php

namespace App\Http\Services;

use App\Http\Repositories\PassengerPricingRepository;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;
use App\Models\PassengerPricing;

class PassengerPricingService
{
    protected $repo;

    public function __construct(PassengerPricingRepository $repo)
    {
        $this->repo = $repo;
    }

    public function list()
    {
        return $this->repo->getAll();
    }

    public function get($id)
    {
        return $this->repo->findById($id);
    }

    public function create(array $data)
    {
        $validator = Validator::make($data, [
            'vehicle_type'          => 'required|in:motor,mobil',
            'departure_terminal_id' => 'required|exists:terminals,id',
            'arrival_terminal_id'   => 'required|exists:terminals,id|different:departure_terminal_id',
            'price_per_seat'        => 'required|integer|min:1000',
            'commision_percentage'  => 'required|integer|min:0|max:100',
        ]);

        if ($validator->fails()) throw new ValidationException($validator);
        return $this->repo->create($data);
    }

    public function update($id, array $data)
    {
        $pricing = PassengerPricing::find($id);
        if (!$pricing) throw ValidationException::withMessages(['pricing' => 'Pricing not found']);

        $validator = Validator::make($data, [
            'vehicle_type'          => 'sometimes|in:motor,mobil',
            'departure_terminal_id' => 'sometimes|exists:terminals,id',
            'arrival_terminal_id'   => 'sometimes|exists:terminals,id|different:departure_terminal_id',
            'price_per_seat'        => 'sometimes|integer|min:1000',
            'commision_percentage'  => 'sometimes|integer|min:0|max:100',
        ]);

        if ($validator->fails()) throw new ValidationException($validator);
        return $this->repo->update($pricing, $data);
    }

    public function delete($id)
    {
        $pricing = PassengerPricing::find($id);
        if (!$pricing) return false;
        return $this->repo->delete($pricing);
    }
}
