<?php

namespace App\Http\Services;

use App\Http\Repositories\ChatRepository;
use Exception;

class ChatService {

    protected $repo;

    public function __construct(ChatRepository $repo)
    {
        $this->repo = $repo;
    }

    public function listChats($userId){
        try {
            $chats = $this->repo->getAllChats($userId);
            return [
                'success' => true,
                'data' => $chats,
                'message' => 'Chats retrieved successfully.'
            ];
        } catch(Exception $e){
            return [
                'success' => false,
                'data' => null,
                'message' => 'Failed to retrieve chats: '.$e->getMessage()
            ];
        }
    }

    public function getChatWithUser($userId, $otherUserId){
        try {
            $messages = $this->repo->getMessagesWithUser($userId, $otherUserId);
            return [
                'success' => true,
                'data' => $messages,
                'message' => 'Messages retrieved successfully.'
            ];
        } catch(Exception $e){
            return [
                'success' => false,
                'data' => null,
                'message' => 'Failed to retrieve messages: '.$e->getMessage()
            ];
        }
    }

    public function sendChat($fromId, $toId, $body, $type = 'text'){
        try {
            $message = $this->repo->sendMessage([
                'from_id' => $fromId,
                'to_id'   => $toId,
                'body'    => $body,
                'type'    => $type,
            ]);
            return [
                'success' => true,
                'data' => $message,
                'message' => 'Message sent successfully.'
            ];
        } catch(Exception $e){
            return [
                'success' => false,
                'data' => null,
                'message' => 'Failed to send message: '.$e->getMessage()
            ];
        }
    }
}
