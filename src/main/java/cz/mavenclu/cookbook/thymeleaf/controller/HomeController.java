package cz.mavenclu.cookbook.thymeleaf.controller;

import cz.mavenclu.cookbook.thymeleaf.dto.RecipeItemWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.SearchObjectForm;
import cz.mavenclu.cookbook.thymeleaf.service.FeederService;
import cz.mavenclu.cookbook.thymeleaf.service.RecipeWebService;
import cz.mavenclu.cookbook.thymeleaf.util.ControllerModelPopulateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class HomeController {


    private final RecipeWebService recipeWebService;
    private final FeederService feederService;

    public HomeController(RecipeWebService recipeWebService, FeederService feederService) {
        this.recipeWebService = recipeWebService;
        this.feederService = feederService;
    }







}