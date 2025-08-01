package com.rr.ai.mcp.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIPromptWithMCPServerService {

    private final ChatClient chatClient;

    public AIPromptWithMCPServerService(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(tools)
                .build();
    }

    public <T> List<T> getResponse(Prompt prompt, ParameterizedTypeReference<List<T>> typeReference){
        return this.chatClient.prompt(prompt)
                .call()
                .entity(typeReference);
    }
}
