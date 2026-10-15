package com.chatbot.aichatbot.repository;
import com.chatbot.aichatbot.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByOrderByCreatedAtAsc();
}

