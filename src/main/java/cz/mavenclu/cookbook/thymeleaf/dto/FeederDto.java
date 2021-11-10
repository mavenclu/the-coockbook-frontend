package cz.mavenclu.cookbook.thymeleaf.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeederDto {

    private long id;
    private String name;
    private String chefId;
    private List<Allergen> allergens;
    private List<IngredientWebDto> intolerances;

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeederDto)) return false;
        FeederDto feederDto = (FeederDto) o;
        return getId() == feederDto.getId() && getName().equals(feederDto.getName()) && Objects.equals(getChefId(), feederDto.getChefId()) && Objects.equals(getAllergens(), feederDto.getAllergens()) && Objects.equals(getIntolerances(), feederDto.getIntolerances());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getChefId(), getAllergens(), getIntolerances());
    }
}
