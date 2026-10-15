package com.chatbot.aichatbot.service;

import com.chatbot.aichatbot.dto.ChatRequest;
import com.chatbot.aichatbot.dto.ChatResponse;
import com.chatbot.aichatbot.entity.ChatMessage;
import com.chatbot.aichatbot.repository.ChatMessageRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final ChatMessageRepository repository;

    public ChatService(
            ChatClient.Builder chatClientBuilder,
            ChatMessageRepository repository) {

        this.chatClient = chatClientBuilder.build();
        this.repository = repository;
    }

    public ChatResponse sendMessage(ChatRequest request) {

        String question = request.message().trim();

        String answer = chatClient.prompt()
                .user(question)
                .call()
                .content();

        if (answer == null || answer.isBlank()) {
            throw new IllegalStateException(
                    "The AI returned an empty response");
        }

        ChatMessage saved = new ChatMessage();
        saved.setUserMessage(question);
        saved.setBotResponse(answer);

        ChatMessage result = repository.save(saved);

        return toResponse(result);
    }

    public List<ChatResponse> getHistory() {
        return repository.findAllByOrderByCreatedAtAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ChatResponse toResponse(ChatMessage message) {
        return new ChatResponse(
                message.getId(),
                message.getUserMessage(),
                message.getBotResponse(),
                message.getCreatedAt().toString()
        );
    }
}
