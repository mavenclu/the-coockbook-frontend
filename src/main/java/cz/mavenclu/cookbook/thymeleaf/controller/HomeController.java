package cz.mavenclu.cookbook.thymeleaf.controller;

import cz.mavenclu.cookbook.thymeleaf.dto.RecipeItemWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.SearchObjectForm;
import cz.mavenclu.cookbook.thymeleaf.service.FeederService;
import cz.mavenclu.cookbook.thymeleaf.service.RecipeWebService;
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

    @GetMapping("/")
    public String home(Model model, @RequestAttribute("accessToken") String accessToken) {
        model.addAttribute("recipes", recipeWebService.getAllRecipes(accessToken));
        addAttributesToModel(model, accessToken);
        return "index";
    }

    @PostMapping("/recipes/filter")
    public String filterRecipes(Model model, @ModelAttribute SearchObjectForm searchForm,
                                BindingResult bindingResult, @RequestAttribute("accessToken") String accessToken){
        log.info("filterRecipes - filtering recipes with param: {}", searchForm.getCuisine());
        addAttributesToModel(model, accessToken);

        if (bindingResult.hasErrors() || searchForm.getCuisine() == null){
            log.info("filterRecipes - errors found in model. Redirecting home.");
            return "redirect:/";
        } else {

            log.info("filterRecipes - calling recipe service");
            List<RecipeWebDto> filteredResult = recipeWebService.findRecipesByCuisine(searchForm.getCuisine().getLabel(), accessToken);
            log.info("filterRecipes - got filtered result: {}", filteredResult);
            model.addAttribute("recipes", filteredResult);
            return "index";
        }
    }

    private void addAttributesToModel(Model model, String accessToken){
        model.addAttribute("cuisine", RecipeWebDto.Cuisine.values());
        model.addAttribute("diet", RecipeWebDto.Diet.values());
        model.addAttribute("difficulty", RecipeWebDto.Difficulty.values());
        model.addAttribute("measure", RecipeItemWebDto.Measure.values());
        model.addAttribute("searchForm", new SearchObjectForm());
        model.addAttribute("feeders", feederService.findAll(accessToken));
    }

}