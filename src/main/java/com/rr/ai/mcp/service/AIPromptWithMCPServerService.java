package com.rr.ai.mcp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@ConditionalOnProperty(name = "spring.ai.mcp.client.enabled", havingValue = "true", matchIfMissing = true)
public class AIPromptWithMCPServerService {

    private final ChatClient chatClient;

    public AIPromptWithMCPServerService(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(tools)
                .build();
    }

    public String getResponse(Prompt prompt, ParameterizedTypeReference<String> typeReference){
        log.info("inside getResponse prompt:{}", prompt.getContents());
        return this.chatClient.prompt(prompt)
                .call()
                .content();
    }
}
