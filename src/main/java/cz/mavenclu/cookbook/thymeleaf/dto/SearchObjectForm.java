package cz.mavenclu.cookbook.thymeleaf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchObjectForm {

    private RecipeWebDto.Difficulty difficulty;
    private RecipeWebDto.Diet diet;
    private RecipeWebDto.Cuisine cuisine;
    private List<String> consumersIdList = new ArrayList<>();
    private RecipeWebDto.TotalCookingTime requiredTime;
}
