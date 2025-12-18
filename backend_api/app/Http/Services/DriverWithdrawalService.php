<?php

namespace App\Http\Services;

use App\Http\Repositories\DriverWithdrawalRepository;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;

class DriverWithdrawalService
{
    protected $driverWithdrawalRepository;

    public function __construct(DriverWithdrawalRepository $repo)
    {
        $this->driverWithdrawalRepository = $repo;
    }

    // List semua withdrawal
    public function listWithdrawals()
    {
        return $this->driverWithdrawalRepository->getAll();
    }

    // Ambil withdrawal berdasarkan ID
    public function getWithdrawal($id)
    {
        return $this->driverWithdrawalRepository->findById($id);
    }

    // Ambil berdasarkan driver
    public function listByDriver($driverId)
    {
        return $this->driverWithdrawalRepository->getByDriver($driverId);
    }

    // Ambil berdasarkan status
    public function listByStatus($status)
    {
        return $this->driverWithdrawalRepository->getByStatus($status);
    }

    // Buat withdrawal baru
    public function createWithdrawal(array $data)
    {
        $validator = Validator::make($data, [
            'driver_id' => 'required|exists:drivers,id',
            'withdrawal_amount' => 'required|integer|min:1000',
            'bank_name' => 'required|string|max:255',
            'account_name' => 'required|string|max:255',
            'account_number' => 'required|string|max:50',
            'status' => 'nullable|string|in:pending,diterima,ditolak',
            'rejected_reason' => 'nullable|string|max:255',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        // Default status = Pending
        $data['status'] = $data['status'] ?? 'pending';

        return $this->driverWithdrawalRepository->create($data);
    }

    // Update withdrawal (biasanya untuk ubah status)
    public function updateWithdrawal($id, array $data)
    {
        $validator = Validator::make($data, [
            'withdrawal_amount' => 'sometimes|integer|min:1000',
            'bank_name' => 'sometimes|string|max:255',
            'account_name' => 'sometimes|string|max:255',
            'account_number' => 'sometimes|string|max:50',
            'status' => 'sometimes|string|in:pending,diterima,ditolak',
            'rejected_reason' => 'nullable|string|max:255',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->driverWithdrawalRepository->update($id, $data);
    }

    // Hapus withdrawal
    public function deleteWithdrawal($id)
    {
        return $this->driverWithdrawalRepository->delete($id);
    }
}
