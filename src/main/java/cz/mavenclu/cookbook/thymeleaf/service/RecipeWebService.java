package cz.mavenclu.cookbook.thymeleaf.service;



import cz.mavenclu.cookbook.thymeleaf.dto.RecipeForm;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeWebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class RecipeWebService {

    private final WebClient webClient;

    @Value("${cookbook.rest.resource.all-recipes}")
    private String allRecipesUrl;

    @Value("${cookbook.rest.resource.recipe-by-id}")
    private String recipeById;

    @Value("${cookbook.rest.resource.recipes-search}")
    private String searchRecipesUrl;


    public RecipeWebService(WebClient webClient) {
        this.webClient = webClient;
    }



    public RecipeWebDto getRecipeWithWebClient(Long id){
        log.info("getRecipeWithWebClient() - calling WebClient to look for recipe with ID: {}", id);
        Mono<RecipeWebDto> recipeMono =  webClient
                .get()
                .uri(recipeById, id)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()){
                        return response.bodyToMono(RecipeWebDto.class);
                    } else
                        return Mono.empty();

                });

        log.info("getRecipeWithWebClient() - WebClient retrieved: {}", recipeMono);
        RecipeWebDto recipe = recipeMono.block();
        log.info("getRecipeWithWebClient() - got: {}", recipe);
        return recipe;
    }

    public List<RecipeWebDto> getAllRecipes(){
        Mono<List<RecipeWebDto>> response = webClient
                .get()
                .uri(allRecipesUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        List<RecipeWebDto> recipes = response.block();
        log.info("getAllRecipes() - ");
        return recipes;
    }

    public void saveRecipe(RecipeForm recipeForm) {
        log.info("saveRecipe() - with params: recipe form {} ", recipeForm);
        log.info("saveRecipe() - creating Mono of recipeForm");
        Mono<RecipeForm> recipe = Mono.just(recipeForm);
        log.info("saveRecipe() - created: {}", recipe);
        webClient
                .post()
                .uri(allRecipesUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(recipe,  RecipeForm.class)
                .retrieve()
                .bodyToMono(RecipeForm.class)
                .block()
        ;
        log.info("saveRecipe() - sent post request");

    }


    public List<RecipeWebDto> findRecipesByCuisine(String cuisine) {
        log.error("LOOKING FOR RECIPES BY CUISINE");
        Mono<List<RecipeWebDto>> filteredResponse = webClient
                .get()
                .uri(searchRecipesUrl, cuisine)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        List<RecipeWebDto> filteredRecipes = filteredResponse.block();
        log.error("findRecipesByCuisine - found: {}", filteredRecipes);
        return filteredRecipes;
    }
}
