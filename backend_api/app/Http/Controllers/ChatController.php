<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Services\ChatService;
use App\Http\Resources\MessageResource;

class ChatController extends Controller
{
    protected $service;

    public function __construct(ChatService $service){
        $this->service = $service;
    }
    /**
     * Display a listing of the resource.
     */
    public function index(Request $request)
    {
        $chats = $this->service->listChats($request->user()->id);
        return MessageResource::collection($chats);
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(Request $request, $userId)
    {
        $message = $this->service->getChatWithUser($request->user()->id, $userId);
        return MessageResource::collection($message);
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(string $id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }

    public function send(Request $request, $userId){
    $request->validate([
        'body' => 'required|string',
        'type' => 'nullable|string',
    ]);

    $result = $this->service->sendChat(
        $request->user()->id,
        $userId,
        $request->body,
        $request->type ?? 'text'
    );

    if($result['success']){
        return response()->json([
            'status' => 'success',
            'message' => $result['message'],
            'data' => $result['data']
        ], 201); // 201 = created
    }

    return response()->json([
        'status' => 'error',
        'message' => $result['message']
    ], 500);
}

}
