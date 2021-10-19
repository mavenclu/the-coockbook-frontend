package cz.mavenclu.cookbook.thymeleaf.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeForm {

    @NotNull
    @Size(min = 3, max = 90, message = "title should contain at least 3 characters and at most 90")
    private String title;
    @Size(min = 0, max = 160, message = "recipe description should be at most 160 characters long")
    private String description;
    @Min(0)
    private int prepTime;
    @Min(0)
    private int cookingTime;
    @Min(1)
    private int yields;
    private RecipeWebDto.Difficulty difficulty;
    private RecipeWebDto.Cuisine cuisine;
    private RecipeWebDto.Diet diet;

    List<RecipeItemForm> ingredients = new ArrayList<>();
    List<String> instructions = new ArrayList<>();

}
