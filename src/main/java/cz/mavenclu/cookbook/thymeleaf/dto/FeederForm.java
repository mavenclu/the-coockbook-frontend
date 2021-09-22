package cz.mavenclu.cookbook.thymeleaf.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FeederForm {

    @NotNull
    @NotBlank
    private String name;
}
