package cz.mavenclu.cookbook.thymeleaf.controller;

import cz.mavenclu.cookbook.thymeleaf.dto.IngredientWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeForm;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeItemForm;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeItemWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.SearchObjectForm;
import cz.mavenclu.cookbook.thymeleaf.service.FeederService;
import cz.mavenclu.cookbook.thymeleaf.service.IngredientWebService;
import cz.mavenclu.cookbook.thymeleaf.service.RecipeWebService;
import cz.mavenclu.cookbook.thymeleaf.util.ControllerModelPopulateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;

@Slf4j
@Controller
public class RecipeMvcController {

    private final RecipeWebService recipeWebService;
    private final IngredientWebService ingredientWebService;
    private final FeederService feederService;

    public RecipeMvcController(RecipeWebService recipeWebService, IngredientWebService ingredientWebService, FeederService feederService) {
        this.recipeWebService = recipeWebService;
        this.ingredientWebService = ingredientWebService;
        this.feederService = feederService;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        ControllerModelPopulateHelper.addProfileToModel(model, principal);
        model.addAttribute("recipes", recipeWebService.getAllRecipes());
        model.addAttribute("feeders", feederService.findAll());
        return "index";
    }

    @GetMapping("/recipes/add")
    public String renderRecipeForm(Model model){
        log.info("renderRecipeForm() - creating form");
        addAttributesToModelAddRecipeForm(model);
        log.info("renderForm() - added attributes to form. Form rendered.");
        return "add-new-recipe-form";
    }




    @PostMapping("/recipes/save")
    public String saveRecipe(@ModelAttribute RecipeForm recipeForm, Model model, BindingResult result){
        log.info("saveRecipe() - checking for errors");
        if (result.hasErrors() ) {
            log.warn("saveRecipe() - errors found: {}", result.getAllErrors());
            return "error";
        }
        else {
            log.info("saveRecipe() - no errors. calling RecipeWebService to save recipe");
            recipeWebService.saveRecipe(recipeForm);

            log.info("saveRecipe() - recipe saved.");
            model.addAttribute("recipes", recipeWebService.getAllRecipes());
            log.info("saveRecipe() - got all recipes from db, added to model, redirecting to homepage");
            return "redirect:/";
        }

    }

    @PostMapping("/recipes/filter")
    public String filterRecipes(Model model, @ModelAttribute SearchObjectForm searchForm,
                                BindingResult bindingResult){
        log.info("filterRecipes - filtering recipes with param: {}", searchForm.getCuisine());
        if (bindingResult.hasErrors() || searchForm.getCuisine() == null){
            log.info("filterRecipes - errors found in model. Redirecting home.");
            return "redirect:/";
        } else {

            log.info("filterRecipes - calling recipe service");
            List<RecipeWebDto> filteredResult = recipeWebService.findRecipesByCuisine(searchForm.getCuisine().getLabel());
            log.info("filterRecipes - got filtered result: {}", filteredResult);
            model.addAttribute("recipes", filteredResult);
            model.addAttribute("feeders", feederService.findAll());
            return "index";
        }
    }



    @GetMapping("/recipes/{id}")
    public String showRecipe(Model model, @PathVariable Long id){
        try {

            log.info("showRecipeTemplate() - populating template by recipe with ID: {}", id);
            RecipeWebDto recipe = recipeWebService.getRecipeWithWebClient(id);
            log.info("showRecipeTemplate() - got recipe with ID: {}", recipe.getId());
            log.info("showRecipeTemplate() - adding recipe to model");
            model.addAttribute("recipe", recipe);
            log.info("showRecipeTemplate() - rendering template");
            return "recipe-post";
        } catch (WebClientResponseException | TemplateInputException clientResponseException) {
            log.error("showRecipeTemplate() - caught PSQL Exception {}", clientResponseException.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Failed to render template", clientResponseException
            );
        }
    }

    private void addAttributesToModelAddRecipeForm(Model model){
        RecipeForm recipe = new RecipeForm();
        recipe.getInstructions().add("");
        recipe.getInstructions().add("");
        recipe.getInstructions().add("");
        recipe.getIngredients().add(new RecipeItemForm());
        recipe.getIngredients().add(new RecipeItemForm());
        recipe.getIngredients().add(new RecipeItemForm());

        model.addAttribute("recipe", recipe);
        model.addAttribute("cuisine", RecipeWebDto.Cuisine.values());
        model.addAttribute("diet", RecipeWebDto.Diet.values());
        model.addAttribute("difficulty", RecipeWebDto.Difficulty.values());
        model.addAttribute("measure", RecipeItemWebDto.Measure.values());
        List<IngredientWebDto> ingredients = ingredientWebService.getAllIngredients();
        model.addAttribute("ingredientsFromDb", ingredients);

    }

}
