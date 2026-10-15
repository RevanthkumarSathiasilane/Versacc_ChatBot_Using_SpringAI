package com.chatbot.aichatbot.dto;

public record ChatResponse(
        Long id,
        String userMessage,
        String botResponse,
        String createdAt
) {
}
