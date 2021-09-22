package cz.mavenclu.cookbook.thymeleaf.dto;


import lombok.Data;

import java.util.List;
@Data
public class FeederDto {

    private long id;
    private String name;
    private String chefId;
    private List<Allergen> allergens;
    private List<IngredientWebDto> intolerances;

}
