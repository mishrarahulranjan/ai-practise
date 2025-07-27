package com.rr.ai.advisor.api;

import com.rr.ai.advisor.model.Animal;
import com.rr.ai.advisor.service.AIPromptWithAdvisorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/advisor")
@Slf4j
public class GenerateEntityWithAdvisorController {

    private final AIPromptWithAdvisorService aiPromptService;

    public GenerateEntityWithAdvisorController(AIPromptWithAdvisorService aiPromptService) {
        this.aiPromptService = aiPromptService;
    }

    @GetMapping("/animals")
    List<Animal> generateAnimalEntity() {
        PromptTemplate pt = new PromptTemplate("""
                Return a current list of 10 famous Animal if exists or generate a new list with random values.
                Each object should contain an auto-incremented id field.
                Do not include any explanations or additional text.
                """);

        List<Animal>  animals = aiPromptService.getResponse(pt.create(), new ParameterizedTypeReference<>() {
        });

        log.info("generate Animal Entity list {}", animals);
        return animals;
    }

    @GetMapping("/animals/{id}")
    List<Animal> getAnimal(@PathVariable String id) {
        PromptTemplate pt = new PromptTemplate("""
                Find and return the object with id {id} in a current list of Animal.
                """);
        Prompt p = pt.create(Map.of("id", id));

        List<Animal>  animals = aiPromptService.getResponse(p, new ParameterizedTypeReference<>() {});

        log.info("fetched Animal Entity list {}", animals);
        return animals;
    }

}