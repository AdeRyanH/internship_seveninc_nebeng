<?php

namespace App\Http\Repositories;

use App\Models\ChMessage;
use Chatify\Models\Favorite;

class ChatRepository
{
    // Ambil semua percakapan user
    public function getAllChats($userId)
    {
        return ChMessage::where('from_id', $userId)
            ->orWhere('to_id', $userId)
            ->orderBy('created_at', 'desc')
            ->get();
    }

    // Ambil percakapan dengan user tertentu
    public function getMessagesWithUser($userId, $otherUserId)
    {
        return ChMessage::where(function($q) use ($userId, $otherUserId){
            $q->where('from_id', $userId)->where('to_id', $otherUserId);
        })->orWhere(function($q) use ($userId, $otherUserId){
            $q->where('from_id', $otherUserId)->where('to_id', $userId);
        })->orderBy('created_at', 'asc')->get();
    }

    // Kirim pesan
    public function sendMessage($data)
    {
        return ChMessage::create($data);
    }
}
