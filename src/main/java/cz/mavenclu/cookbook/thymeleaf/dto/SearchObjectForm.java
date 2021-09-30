package cz.mavenclu.cookbook.thymeleaf.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchObjectForm {
    private RecipeWebDto.Difficulty difficulty;
    private RecipeWebDto.Diet diet;
    private RecipeWebDto.Cuisine cuisine;
    private List<FeederDto> consumers;
}
