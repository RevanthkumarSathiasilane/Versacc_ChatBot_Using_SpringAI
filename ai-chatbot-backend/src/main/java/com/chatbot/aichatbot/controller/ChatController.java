package com.chatbot.aichatbot.controller;

import com.chatbot.aichatbot.dto.ChatRequest;
import com.chatbot.aichatbot.dto.ChatResponse;
import com.chatbot.aichatbot.service.ChatService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse sendMessage(
            @Valid @RequestBody ChatRequest request) {
        return chatService.sendMessage(request);
    }

    @GetMapping("/history")
    public List<ChatResponse> getHistory() {
        return chatService.getHistory();
    }
}
