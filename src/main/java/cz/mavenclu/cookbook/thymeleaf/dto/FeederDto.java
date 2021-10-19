package cz.mavenclu.cookbook.thymeleaf.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Data
public class FeederDto {

    private long id;
    private String name;
    private String chefId;
    private List<Allergen> allergens;
    private List<IngredientWebDto> intolerances;


}
