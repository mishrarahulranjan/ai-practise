package com.rr.ai.functions.service;

import com.rr.ai.functions.model.Animal;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Description("Animal Data analyser, to find the strongest animal")
public class DataAnalyzerService {

    @Tool(description="to find the strongest animal")
    public Optional<Animal> getStrongestAnimal(List<Animal> animalList){
        return animalList.stream().max(Comparator.comparing(Animal::strength));
    }

    @Tool(description="to find the weakest animal")
    public Optional<Animal> getWeakAnimal(List<Animal> animalList){
        return animalList.stream().min(Comparator.comparing(Animal::strength));
    }

    @Tool(description="to find count of animals")
    public int totalAnimal(List<Animal> animalList){
        return animalList.size();
    }
}
