package com.rr.ai.functions.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIPromptWithToolsService {

    private final ChatClient chatClient;

    private final DataAnalyzerService dataAnalyzerService;

    public AIPromptWithToolsService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, DataAnalyzerService dataAnalyzerService) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(PromptChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor())
                .build();
        this.dataAnalyzerService = dataAnalyzerService;
    }

    public <T> List<T> getResponse(Prompt prompt, ParameterizedTypeReference<List<T>> typeReference){
        return this.chatClient.prompt(prompt)
                .call()
                .entity(typeReference);
    }

    public <T> List<T> getResponseWithTools(Prompt prompt, ParameterizedTypeReference<List<T>> typeReference){
        return this.chatClient.prompt(prompt).tools(dataAnalyzerService)
                .call()
                .entity(typeReference);
    }
}
