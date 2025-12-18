<?php

namespace App\Http\Services;

use App\Helpers\DriverStatusHelper;
use Illuminate\Support\Facades\Validator;
use App\Http\Repositories\DriverAdminRepo;
use Illuminate\Validation\ValidationException;

class DriverAdminService
{
    protected $driverRepository;

    public function __construct(DriverAdminRepo $driverRepository)
    {
        $this->driverRepository = $driverRepository;
    }

    // List semua driver
    public function listDrivers($perPage = 10, $filters = [])
    {
        $drivers = $this->driverRepository->paginate($perPage, $filters);

        return $drivers;
    }

    // Ambil driver by ID
    public function getDriver($id)
    {
        return $this->driverRepository->findById($id);
    }

    // Buat driver baru
    public function createDriver(array $data)
    {
        $validator = Validator::make($data, [
            'user_id'                               => 'required|exists:users,id',
            'full_name'                             => 'required|string|max:255',
            'telephone'                             => 'required|string|max:20',
            'full_address'                          => 'nullable|string|max:255',
            'profile_img'                           => 'nullable|string|max:255',
            'balance'                               => 'nullable|integer|min:0',
            'credit_score'                          => 'nullable|integer|min:0',

            // ID Card
            'face_img'                              => 'nullable|string|max:255',
            'face_with_id_img'                      => 'nullable|string|max:255',
            'id_card_img'                           => 'nullable|string|max:255',
            'id_card_number'                        => 'nullable|string|max:50',
            'id_card_fullname'                      => 'nullable|string|max:255',
            'id_card_birthdate'                     => 'nullable|date',
            'id_card_verified'                      => 'nullable|boolean',
            'id_card_rejected_reason'               => 'nullable|string|max:255',

            // Driver License
            'driver_license_number'                 => 'nullable|string|max:50',
            'driver_license_type'                   => 'nullable|string|max:10',
            'driver_license_expired'                => 'nullable|date',
            'driver_license_img'                    => 'nullable|string|max:255',
            'driver_license_verified'               => 'nullable|boolean',
            'driver_license_rejected_reason'        => 'nullable|string|max:255',

            // Police Clearance
            'police_clearance_certificate_number'   => 'nullable|string|max:50',
            'police_clearance_certificate_fullname' => 'nullable|string|max:255',
            'police_clearance_certificate_expired'  => 'nullable|date',
            'police_clearance_certificate_img'      => 'nullable|string|max:255',
            'police_clearance_verified'             => 'nullable|boolean',
            'police_clearance_rejected_reason'      => 'nullable|string|max:255',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->driverRepository->create($data);
    }

    // Update driver
    public function updateDriver($id, array $data)
    {
        $validator = Validator::make($data, [
            'full_name'                 => 'sometimes|required|string|max:255',
            'telephone'                 => 'sometimes|required|string|max:20',
            'full_address'              => 'nullable|string|max:255',
            'profile_img'               => 'nullable|string|max:255',
            'balance'                   => 'nullable|integer|min:0',
            'credit_score'              => 'nullable|integer|min:0',
            'id_card_rejected_reason'   => 'nullable|string',

            // Dokumen verifikasi
            'id_card_verified'          => 'nullable|boolean',
            'driver_license_verified'   => 'nullable|boolean',
            'police_clearance_verified' => 'nullable|boolean',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->driverRepository->update($id, $data);
    }

    // Hapus driver
    public function deleteDriver($id)
    {
        return $this->driverRepository->delete($id);
    }

    public function verifyDocument($id, $type, $status, $reason = null){
        $driver = $this->driverRepository->findById($id);

        if(!$driver){
            return response()->json(['message' => 'Driver not found'], 404);
        }

        $verifyField = null;
        $reasonField = null;

        switch($type) {
            case 'id_card':
                $verifyField = 'id_card_verified';
                $reasonField = 'id_card_rejected_reason';
                break;

            case 'driver_license':
                $verifyField = 'driver_license_verified';
                $reasonField = 'driver_license_rejected_reason';
                break;

            case 'police_clearance':
                $verifyField = 'police_clearance_verified';
                $reasonField = 'police_clearance_rejected_reason';
                break;

        }

        $data = [
            $verifyField => $status,
            $reasonField => $reason,
        ];

        $this->driverRepository->update($id, $data);
        return response()->json(['message' => 'Document verification updated successfully']);
    }
}
