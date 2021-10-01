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
        DIARY_FREE("Diary free"),
        GLUTEN_FREE("Gluten free"),
        HISTAMINE_FREE("Low histamine"),
        OMNIVORE("Omnivore"),
        REFINE_SUGARS_FREE("Refine sugars free"),
        VEGAN("Vegan"),
        VEGETARIAN("Vegetarian");


        private final String label;

        Diet(String value){
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
        EASY("Easy", "success"),
        MEDIUM("Ok", "warning"),
        HARD("Hard", "danger");

        private final String label;
        private final String frontEndHelper;

        Difficulty(String value, String frontEndHelper){
            this.label = value;
            this.frontEndHelper = frontEndHelper;
        }

        public String getLabel() {
            return label;
        }
        public String getFrontEndHelper(){
            return frontEndHelper;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public enum Cuisine {
        AMERICAN("American"),
        ASIAN("Asian"),
        CZECH("Czech"),
        INDIAN("Indian"),
        MEDITERRANEAN("Mediterranean"),
        THAI("Thai"),
        OTHER("Other");

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
