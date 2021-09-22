package cz.mavenclu.cookbook.thymeleaf.service;

import cz.mavenclu.cookbook.thymeleaf.dto.FeederDto;
import cz.mavenclu.cookbook.thymeleaf.dto.FeederForm;
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
public class FeederService {
    @Value("${cookbook.rest.resource.all-feeders}")
    private String feedersUrl;

    private final WebClient webClient;

    public FeederService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void saveFeeder(FeederForm feeder, String tokenValue) {

        log.info("saveFeeder - feeder to save: {} and token: {}", feeder, tokenValue);
        Mono<FeederForm> feederMono = Mono.just(feeder);
        webClient
                .post()
                .uri(feedersUrl)
                .headers(headers -> headers.setBearerAuth(tokenValue))
                .contentType(MediaType.APPLICATION_JSON)
                .body(feederMono, FeederForm.class)
                .retrieve()
                .bodyToMono(FeederForm.class)
                .block()
                ;
    }

    public List<FeederDto> findAll(String accessToken) {
        Mono<List<FeederDto>> response = webClient
                .get()
                .uri(feedersUrl)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {})
                ;
        log.info("findAll - performed get request for all ingredients");
        List<FeederDto> allFeeders = response.block();
        log.info("findAll - found all feeders");
        return allFeeders;

    }
}
