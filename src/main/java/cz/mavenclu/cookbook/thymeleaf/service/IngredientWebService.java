package cz.mavenclu.cookbook.thymeleaf.service;

//import cz.mavenclu.cookbook.thymeleaf.config.ResourceApiConfig;
import cz.mavenclu.cookbook.thymeleaf.dto.IngredientWebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class IngredientWebService {


    private final WebClient webClient;
//    private final ResourceApiConfig urlConfig;

    public IngredientWebService(WebClient webClient
//            , ResourceApiConfig urlConfig
    ) {
        this.webClient = webClient;
//        this.urlConfig = urlConfig;
    }

    public List<IngredientWebDto> getAllIngredients() {
        log.info("getAllIngredients");
        Mono<List<IngredientWebDto>> response = webClient
                .get()
                .uri("/cookbook/ingredients")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        List<IngredientWebDto> ingredients = response.block();
        log.info("getAllIngredients");
        return ingredients;

    }
}
