package com.rr.ai.simple.service;

import com.rr.ai.advisor.model.Animal;
import com.rr.ai.simple.model.Person;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIPromptService {

    private final ChatClient chatClient;

    public AIPromptService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public <T> List<T> getResponse(PromptTemplate template, ParameterizedTypeReference<List<T>> typeReference){
        return this.chatClient.prompt(template.create())
                .call()
                .entity(typeReference);
    }
}
