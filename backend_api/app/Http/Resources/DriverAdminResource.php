<?php

namespace App\Http\Resources;

use App\Helpers\DriverStatusHelper;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class DriverAdminResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            "id" => $this->id,
            "user_id" => $this->user_id,
            "full_name" => $this->full_name,
            "telephone" => $this->telephone,
            "full_address" => $this->full_address,
            "profile_img" => $this->profile_img,
            "balance" => $this->balance,
            "credit_score" => $this->credit_score,
            "total_rating" => $this->total_rating,
            "rating_count" => $this->rating_count,
            "average_rating" => $this->average_rating,
            'status_driver' => DriverStatusHelper::getStatus($this),

            "id_card" => [
                "number" => $this->id_card_number,
                "fullname" => $this->id_card_fullname,
                "birthdate" => $this->id_card_birthdate,
                "img" => $this->id_card_img,
                "verified" => (bool) $this->id_card_verified,
                "rejected_reason" => $this->id_card_rejected_reason,
            ],

            "driver_license" => [
                "number" => $this->driver_license_number,
                "type"   => $this->driver_license_type,
                "expired" => $this->driver_license_expired,
                "img" => $this->driver_license_img,
                "verified" => (bool) $this->driver_license_verified,
                "rejected_reason" => $this->driver_license_rejected_reason,
            ],

            "police_certificate" => [
                "number" => $this->police_clearance_certificate_number,
                "fullname" => $this->police_clearance_certificate_fullname,
                "expired" => $this->police_clearance_certificate_expired,
                "img" => $this->police_clearance_certificate_img,
                "verified" => (bool) $this->police_clearance_verified,
                "rejected_reason" => $this->police_clearance_rejected_reason,
            ],

            "face_img" => $this->face_img,
            "face_with_id_img" => $this->face_with_id_img,

            "created_at" => $this->created_at?->toISOString(),
            "updated_at" => $this->updated_at?->toISOString(),

            // --- USER (rapi)
            "user" => [
                "id" => $this->user->id,
                "name" => $this->user->name,
                "username" => $this->user->username,
                "email" => $this->user->email,
                "user_type" => $this->user->user_type,
                "banned" => (bool) $this->user->banned,
                "created_at" => $this->user->created_at?->toISOString(),
                "updated_at" => $this->user->updated_at?->toISOString(),
            ],

            // --- VEHICLES (manual mapping)
            "vehicles" => $this->vehicles->map(function ($v) {
                return [
                    "id" => $v->id,
                    "registration_number" => $v->registration_number,
                    "registration_year" => $v->registration_year,
                    "registration_expired" => $v->registration_expired,
                    "registration_img" => $v->registration_img,
                    "vehicle_name" => $v->vehicle_name,
                    "vehicle_color" => $v->vehicle_color,
                    "vehicle_type" => $v->vehicle_type,
                    "vehicle_img" => $v->vehicle_img,
                    "vehicle_verified" => (bool) $v->vehicle_verified,
                    "vehicle_rejected_reason" => $v->vehicle_rejected_reason,
                    "created_at" => $v->created_at?->toISOString(),
                    "updated_at" => $v->updated_at?->toISOString(),
                ];
            }),

            // --- RATINGS (manual mapping)
            "ratings" => $this->ratings->map(function ($r) {
                return [
                    "id" => $r->id,
                    "customer_id" => $r->customer_id,
                    "rating" => $r->rating,
                    "feedback" => $r->feedback,
                    "created_at" => $r->created_at?->toISOString(),
                ];
            }),
        ];
    }
}
