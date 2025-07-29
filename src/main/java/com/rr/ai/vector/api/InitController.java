package com.rr.ai.vector.api;

import com.rr.ai.vector.db.service.VectorStoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InitController {

    private final VectorStoreService vectorStoreService;

    public InitController(VectorStoreService vectorStoreService) {
        this.vectorStoreService = vectorStoreService;
    }

    @GetMapping("/init")
    String semanticSearch() {
        return vectorStoreService.init();
    }
}
