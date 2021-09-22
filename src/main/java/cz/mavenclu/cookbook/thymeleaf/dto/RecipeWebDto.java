package cz.mavenclu.cookbook.thymeleaf.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class RecipeWebDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 90)
    private String title;

    @NotNull
    @Size(min = 5, max = 160)
    private String description;

    @NotNull
    @Min(0)
    private int prepTime;
    @NotNull
    @Min(0)
    private int cookingTime;

    @NotNull
    private RecipeWebDto.Difficulty difficulty;

    @NotNull
    private Cuisine cuisine;
    @NotNull
    private List<Diet> diets;
    @Min(1)
    private int yields;

    @NotNull
    private List<RecipeItemWebDto> ingredients = new ArrayList<>();

    @NotNull
    List<String> instructions = new ArrayList<>();

    public enum Diet {
        VEGAN("Vegan"),
        OMNIVORE("Omnivore"),
        VEGETARIAN("Vegetarian"),
        GLUTEN_FREE("Gluten free"),
        DIARY_FREE("Diary free"),
        REFINE_SUGARS_FREE("Refine sugars free"),
        HISTAMINE_FREE("Foods with no or low histamine contain");


        private final String label;

        private Diet(String value){
            label = value;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public enum Difficulty {
        EASY("Easy"),
        MEDIUM("Ok"),
        HARD("Hard");

        private final String label;

        private Difficulty(String value){
            this.label = value;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public enum Cuisine {
        CZECH("Czech"),
        ASIAN("Asian"),
        MEDITERRANEAN("Mediterranean"),
        AMERICAN("American"),
        THAI("Thai"),
        INDIAN("Indian"),
        OTHER("other");

        private final String label;

        private Cuisine(String value){
            this.label = value;
        }

        public String getLabel() {
            return label;
        }
        @Override
        public String toString() {
            return label;
        }
    }



}
