<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class CustomerAdminResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'id'                     => $this->id,
            'full_name'              => $this->full_name,
            'full_address'           => $this->full_address,
            'profile_img'            => $this->profile_img,
            'verified'               => (bool) $this->verified,
            'credit_amount'          => $this->credit_amount,
            'telephone'              => $this->telephone,
            'created_at'             => $this->created_at,
            'user' => [

                'id'                 => $this->user->id,
                'name'               => $this->user->name,
                'email'              => $this->user->email,
                'username'           => $this->user->username,
                "banned"             => (bool) $this->user->banned,
            ]
        ];
    }
}
