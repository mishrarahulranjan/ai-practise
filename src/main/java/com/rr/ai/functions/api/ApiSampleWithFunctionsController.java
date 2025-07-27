package com.rr.ai.functions.api;

import com.rr.ai.functions.model.Animal;
import com.rr.ai.functions.service.AIPromptWithToolsService;
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
@RequestMapping("/api/functions")
@Slf4j
public class ApiSampleWithFunctionsController {

    private final AIPromptWithToolsService aiPromptService;

    public ApiSampleWithFunctionsController(AIPromptWithToolsService aiPromptService) {
        this.aiPromptService = aiPromptService;
    }

    @GetMapping("/animals")
    List<Animal> generateAnimalEntity() {
        PromptTemplate pt = new PromptTemplate("""
                Return a current list of 10 famous Animal if exists or generate a new list with random values.
                Each object should contain an auto-incremented id field.
                Include the strength of animal on scale of 0 to 10.
                Do not include any explanations or additional text.
                """);

        List<Animal>  animals = aiPromptService.getResponse(pt.create(), new ParameterizedTypeReference<>() {});

        log.info("generate Animal Entity list {}", animals);
        return animals;
    }

    @GetMapping("/animals/{strength}")
    Animal getAnimal(@PathVariable String strength) {
        PromptTemplate pt = new PromptTemplate("""
                Find and return the Animal with  {id} strength in a current list of Animal.
                """);
        Prompt prompt = pt.create(Map.of("id", strength));

        Animal  animal = aiPromptService.getResponseWithTools(prompt, Animal.class);
        log.info("fetched Animal Entity list {}", animal);
        return animal;
    }

    @GetMapping("/animals/count")
      int getAnimal() {
        PromptTemplate promptTemplate = new PromptTemplate("""
                count the number of animals in a current list of Animal.
                """);
        int  animalCount = aiPromptService.getResponseWithTools(promptTemplate.create(), Integer.class);

        log.info("fetched Animal count from list {}", animalCount);
        return animalCount;
    }

}