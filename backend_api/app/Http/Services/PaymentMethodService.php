<?php

namespace App\Http\Services;

use App\Http\Repositories\PaymentMethodRepository;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\ValidationException;

class PaymentMethodService
{
    protected $paymentMethodRepository;

    public function __construct(PaymentMethodRepository $repo)
    {
        $this->paymentMethodRepository = $repo;
    }

    // List semua metode
    public function listMethods()
    {
        return $this->paymentMethodRepository->getAll();
    }

    // Detail satu metode
    public function getMethod($id)
    {
        return $this->paymentMethodRepository->findById($id);
    }

    // Tambah metode pembayaran baru
    public function createMethod(array $data)
    {
        $validator = Validator::make($data, [
            'bank_name'     => 'required|string|max:100',
            'account_name'  => 'required|string|max:100',
            'account_number'=> 'required|string|max:50|unique:payment_methods,account_number',
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->paymentMethodRepository->create($data);
    }

    // Update metode pembayaran
    public function updateMethod($id, array $data)
    {
        $validator = Validator::make($data, [
            'bank_name'     => 'sometimes|string|max:100',
            'account_name'  => 'sometimes|string|max:100',
            'account_number'=> 'sometimes|string|max:50|unique:payment_methods,account_number,' . $id,
        ]);

        if ($validator->fails()) {
            throw new ValidationException($validator);
        }

        return $this->paymentMethodRepository->update($id, $data);
    }

    // Hapus metode
    public function deleteMethod($id)
    {
        return $this->paymentMethodRepository->delete($id);
    }
}
