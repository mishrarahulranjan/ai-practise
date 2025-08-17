package com.rr.ai.mcp.api;

import com.rr.ai.mcp.service.AIPromptWithMCPServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/mcp")
@Slf4j
@ConditionalOnProperty(name = "spring.ai.mcp.client.enabled", havingValue = "true", matchIfMissing = true)
public class ApiSampleForMCPClientController {

    private final AIPromptWithMCPServerService aiPromptWithMCPServerService;

    public ApiSampleForMCPClientController(AIPromptWithMCPServerService aiPromptWithMCPServerService) {
        this.aiPromptWithMCPServerService = aiPromptWithMCPServerService;
    }

    @GetMapping("/book/{topic}")
    String generateAnimalEntity(@PathVariable String topic) {
        PromptTemplate pt = new PromptTemplate("""
                Find the two famous Author for topic {Topic}.
                """);

        Prompt p = pt.create(Map.of("Topic", topic));

        String response= aiPromptWithMCPServerService.getResponse(p, new ParameterizedTypeReference<>() {});

        log.info("generate book entity list {}", response);

        return response;
    }

    @GetMapping("/person/{name}")
    String generatePersonsEntity(@PathVariable String name) {
        PromptTemplate pt = new PromptTemplate("""
                Return a list of 10 famous Person with same gender as Name {Name}.
                Do not include any explanations or additional text.
                """);

        Prompt p = pt.create(Map.of("Name", name));
        String response = aiPromptWithMCPServerService.getResponse(p, new ParameterizedTypeReference<>() {});

        log.info("generate person Entity list {}", response);
        return response;
    }
}