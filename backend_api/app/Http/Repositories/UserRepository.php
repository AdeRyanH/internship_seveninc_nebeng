<?php

namespace App\Http\Repositories;

use App\Models\User;
use Illuminate\Support\Facades\Hash;

class UserRepository{

    protected $model;

    public function __construct(User $user) {
        $this->model = $user;
    }

    public function getAllUser()
    {
        return User::orderBy('created_at', 'DESC')->get();
    }

    public function findByEmailOrUsername(string $userIdentifier) {
        return $this->model
            ->where('email', $userIdentifier)
            ->orWhere('username', $userIdentifier)
            ->first();
    }

    public function findUserById($id)
    {
        return User::with(['customer', 'driver'])->findOrFail($id);
    }

    public function createUser(array $data)
    {
        $data['password'] = Hash::make($data['password']);
        return User::create($data);
    }

    public function updateUser($id, array $data)
    {
        $user = User::findOrFail($id);
        if (isset($data['password'])) {
            $data['password'] = Hash::make($data['password']);
        }
        $user->update($data);
        return $user;
    }

    public function deleteUser(User $user)
    {
        return $user->delete();
    }

    public function paginate($perPage = 10, $filters = ['driver', 'customer'], $userTypes = null){
        $query = $this->model
            ->with(['customer', 'driver'])
            ->orderBy('created_at', 'DESC');

        if (!empty($userTypes)) {
            if(is_array($userTypes)){
                $query->whereIn('user_type', $userTypes);
            } else {
                $query->where('user_type', $userTypes);
            }
        }

        // filter
            if($filters['status'] !== null && $filters['status'] !== "") {
                $query->where('banned', $filters['status']);
            }

        // search
        if(!empty($filters['search'])){
            $search = $filters['search'];

            $query->where(function($q) use ($search) {
                $q->where('id', 'LIKE', "%$search%")
                  ->orWhere('email', 'LIKE', "%$search%")
                  ->orWhere('name', 'LIKE', "%$search%")
                  ->orWhere('user_type', 'LIKE', "%$search%");
            });
        }
        return $query->paginate($perPage);
    }
}
