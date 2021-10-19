package cz.mavenclu.cookbook.thymeleaf.service;



import cz.mavenclu.cookbook.thymeleaf.dto.RecipeForm;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.SearchObjectForm;
import cz.mavenclu.cookbook.thymeleaf.exception.FilterRecipesNotRetrievedException;
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
    @Value("${cookbook.rest.resource.recipes-filter}")
    private String filterUrl;


    public RecipeWebService(WebClient webClient) {
        this.webClient = webClient;
    }



    public RecipeWebDto getRecipeWithWebClient(Long id, String idToken){
        log.info("getRecipeWithWebClient() - calling WebClient to look for recipe with ID: {}", id);
        Mono<RecipeWebDto> recipeMono =  webClient
                .get()
                .uri(recipeById, id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(idToken))
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

    public List<RecipeWebDto> getAllRecipes(String idToken){
        Mono<List<RecipeWebDto>> response = webClient
                .get()
                .uri(allRecipesUrl)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(idToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        List<RecipeWebDto> recipes = response.block();
        log.info("getAllRecipes() - ");
        return recipes;
    }

    public void saveRecipe(RecipeForm recipeForm, String idToken) {
        log.info("saveRecipe() - with params: recipe form {} ", recipeForm);
        log.info("saveRecipe() - creating Mono of recipeForm");
        Mono<RecipeForm> recipe = Mono.just(recipeForm);
        log.info("saveRecipe() - created: {}", recipe);
        webClient
                .post()
                .uri(allRecipesUrl)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(idToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(recipe,  RecipeForm.class)
                .retrieve()
                .bodyToMono(RecipeForm.class)
                .block()
        ;
        log.info("saveRecipe() - sent post request");

    }


    public List<RecipeWebDto> findRecipesByCuisine(String cuisine, String idToken) {
        log.info("LOOKING FOR RECIPES BY CUISINE");
        Mono<List<RecipeWebDto>> filteredResponse = webClient
                .get()
                .uri(searchRecipesUrl, cuisine)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(idToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        List<RecipeWebDto> filteredRecipes = filteredResponse.block();
        log.error("findRecipesByCuisine - found: {}", filteredRecipes);
        return filteredRecipes;
    }

    public List<RecipeWebDto> filterRecipes(SearchObjectForm searchForm, String tokenValue) throws FilterRecipesNotRetrievedException {
        log.info("{} - filterRecipes - filtering with param: {}", this.getClass().getSimpleName(), searchForm);
        Mono<SearchObjectForm> bodyParam = Mono.just(searchForm);
        List<RecipeWebDto> filteredRecipes = webClient
                .post()
                .uri(filterUrl)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenValue))
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyParam, SearchObjectForm.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RecipeWebDto>>() {
                })
                .block();

        if (filteredRecipes == null){
            throw new FilterRecipesNotRetrievedException();
        }
        log.info("{} - filterRecipes - retrieved {} number of recipes", this.getClass().getSimpleName(), filteredRecipes.size());
        return filteredRecipes;
    }

    private <T> Mono<T> webClientGetRequest( String url, String token){
        return webClient
                .get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<T>() {
                });

    }

}
