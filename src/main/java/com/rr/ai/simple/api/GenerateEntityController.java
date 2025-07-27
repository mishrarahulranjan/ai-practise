package com.rr.ai.simple.api;

import com.rr.ai.simple.model.Animal;
import com.rr.ai.simple.model.Person;
import com.rr.ai.simple.service.AIPromptService;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GenerateEntityController {

    private final AIPromptService aiPromptService;

    public GenerateEntityController(AIPromptService aiPromptService) {
        this.aiPromptService = aiPromptService;
    }

    @GetMapping("/persons")
    List<Person> generatePersonEntity() {
        PromptTemplate pt = new PromptTemplate("""
                Return a current list of 10 famous persons if exists or generate a new list with random values.
                Each object should contain an auto-incremented id field.
                Do not include any explanations or additional text.
                """);

        return aiPromptService.getResponse(pt, new ParameterizedTypeReference<List<Person>>() {});
    }

    @GetMapping("/animals")
    List<Animal> generateAnimalEntity() {
        PromptTemplate pt = new PromptTemplate("""
                Return a current list of 10 famous Animal if exists or generate a new list with random values.
                Each object should contain an auto-incremented id field.
                Do not include any explanations or additional text.
                """);

        return aiPromptService.getResponse(pt, new ParameterizedTypeReference<List<Animal>>() {});
    }

}