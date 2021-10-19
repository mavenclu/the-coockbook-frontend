package cz.mavenclu.cookbook.thymeleaf.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class FeederForm {

    @NotNull
    @NotBlank
    private String name;

    private String chefId;


    private List<Allergen> allergens;
    private List<IngredientWebDto> intolerances;



    public FeederForm() {
        this.intolerances = new ArrayList<>(5);
        populateIntolerancesList(this.intolerances);
    }


    private void populateIntolerancesList(List<IngredientWebDto> intolerances){
        for (int  i = 1; i <= intolerances.size(); i++) {
            intolerances.add(new IngredientWebDto());
        }

    }
}
