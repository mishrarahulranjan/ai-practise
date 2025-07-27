package com.rr.ai;

import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAIStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringAIStarter.class, args);
    }

    @Bean
    MessageWindowChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(100).build();
    }
}