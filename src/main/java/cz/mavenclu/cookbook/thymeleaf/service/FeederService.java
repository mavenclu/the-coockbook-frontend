package cz.mavenclu.cookbook.thymeleaf.service;

import cz.mavenclu.cookbook.thymeleaf.dto.FeederDto;
import cz.mavenclu.cookbook.thymeleaf.dto.FeederForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FeederService {
    @Value("${cookbook.rest.resource.all-feeders}")
    private String feedersUrl;
    @Value("${cookbook.rest.resource.one-feeder}")
    private String oneFeederUrl;

    private final WebClient webClient;

    public FeederService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void saveFeeder(FeederForm feeder, OidcUser principal) {
        log.info("saveFeeder - feeder to save: {}", feeder);
        feeder.setChefId(principal.getClaims().get("sub").toString().substring(6));
        Mono<FeederForm> feederMono = Mono.just(feeder);
        webClient
                .post()
                .uri(feedersUrl)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(principal.getIdToken().getTokenValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(feederMono, FeederForm.class)
                .retrieve()
                .bodyToMono(FeederForm.class)
                .block()
                ;
    }

    public List<FeederDto> findAll(String idToken) {
        Mono<List<FeederDto>> response = webClient
                .get()
                .uri(feedersUrl)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(idToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {})
                ;
        log.info("findAll - performed get request for all ingredients");
        List<FeederDto> allFeeders = response.block();
        if (allFeeders != null){
            log.info("findAll - found all feeders, size: {}, feeders: {}", allFeeders.size(), allFeeders);
            return allFeeders;
        }else {
            return new ArrayList<>();
        }

    }

    public void deleteFeeder(Long id, String tokenValue) {
        log.info("{} - deleteFeeder - deleting feeder with id: {}", this.getClass().getSimpleName(),  id);
        webClient
                .delete()
                .uri(oneFeederUrl, id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenValue))
                .retrieve()
                .bodyToMono(Void.class).block();

    }
}
