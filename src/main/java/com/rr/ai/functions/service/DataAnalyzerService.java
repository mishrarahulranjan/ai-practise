package com.rr.ai.functions.service;

import com.rr.ai.functions.model.Animal;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Description("Animal Data analyser, to find the strongest animal")
public class DataAnalyzerService {

    @Tool(description="to find the strongest animal")
    public Map<String, Object> getStrongestAnimal(List<Map<String, Object>> animalList){
        return animalList.stream()
                .filter(map -> map.containsKey("strength") && map.get("strength") instanceof Integer)
                .max(Comparator.comparingInt(map -> (Integer) map.get("strength")))
                .orElse(null);
    }

    @Tool(description="to find the weakest animal")
    public Map<String, Object> getWeakAnimal(List<Map<String, Object>> animalList){
        return animalList.stream()
                .filter(map -> map.containsKey("strength") && map.get("strength") instanceof Integer)
                .min(Comparator.comparingInt(map -> (Integer) map.get("strength")))
                .orElse(null);
    }

    @Tool(description="to find count of animals")
    public int totalAnimal(List<Map<String, Object>> animalList){
        return animalList.size();
    }
}
