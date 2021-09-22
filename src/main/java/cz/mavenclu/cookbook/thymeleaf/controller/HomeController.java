package cz.mavenclu.cookbook.thymeleaf.controller;

import cz.mavenclu.cookbook.thymeleaf.service.RecipeWebService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;


@Controller
public class HomeController {

    private final RecipeWebService recipeWebService;

    public HomeController(RecipeWebService recipeWebService) {
        this.recipeWebService = recipeWebService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestAttribute("accessToken") String accessToken) {
        model.addAttribute("recipes", recipeWebService.getAllRecipes(accessToken));
        return "index";
    }


}