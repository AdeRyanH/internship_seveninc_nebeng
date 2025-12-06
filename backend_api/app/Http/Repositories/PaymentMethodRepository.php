<?php

namespace App\Http\Repositories;

use App\Models\PaymentMethod;

class PaymentMethodRepository
{
    protected $model;

    public function __construct(PaymentMethod $paymentMethod)
    {
        $this->model = $paymentMethod;
    }

    // Ambil semua metode pembayaran
    public function getAll()
    {
        return $this->model
            ->orderBy('created_at', 'ASC')
            ->get();
    }

    // Ambil metode berdasarkan ID
    public function findById($id)
    {
        return $this->model->find($id);
    }

    // Tambah metode baru
    public function create(array $data)
    {
        return $this->model->create($data);
    }

    // Update metode pembayaran
    public function update($id, array $data)
    {
        $method = $this->model->findOrFail($id);
        $method->update($data);
        return $method;
    }

    // Hapus metode pembayaran
    public function delete($id)
    {
        $method = $this->model->findOrFail($id);
        return $method->delete();
    }
}
