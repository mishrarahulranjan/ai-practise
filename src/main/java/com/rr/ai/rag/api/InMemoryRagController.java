package com.rr.ai.rag.api;

import com.rr.ai.rag.service.InMemoryVectorStoreRagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
@Slf4j
public class InMemoryRagController {
    private final InMemoryVectorStoreRagService inMemoryVectorStoreRagService;

    public InMemoryRagController(InMemoryVectorStoreRagService ragService) {
        this.inMemoryVectorStoreRagService = ragService;
    }

    @GetMapping("/query")
    public ChatResponse query(@RequestParam String query) {
        ChatResponse queryResponse = inMemoryVectorStoreRagService.getQueryResponse(query);

        log.info("generate response {}", queryResponse);
        return queryResponse;
    }
}
