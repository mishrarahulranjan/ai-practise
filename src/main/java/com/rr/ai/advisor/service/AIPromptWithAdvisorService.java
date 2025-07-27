package com.rr.ai.advisor.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIPromptWithAdvisorService {

    private final ChatClient chatClient;

    public AIPromptWithAdvisorService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(PromptChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor())
                .build();
    }

    public <T> List<T> getResponse(Prompt prompt, ParameterizedTypeReference<List<T>> typeReference){
        return this.chatClient.prompt(prompt)
                .call()
                .entity(typeReference);
    }
}
