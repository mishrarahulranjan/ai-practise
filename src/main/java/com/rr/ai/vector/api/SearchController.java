package com.rr.ai.vector.api;

import com.rr.ai.vector.db.service.VectorStoreService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class SearchController {

    private final VectorStoreService vectorStoreService;

    public SearchController(VectorStoreService vectorStoreService) {
        this.vectorStoreService = vectorStoreService;
    }

    @GetMapping("/search/{query}")
    String semanticSearch(@PathVariable String query) {
        return vectorStoreService.semanticSearch(query);
    }
}
