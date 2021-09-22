package cz.mavenclu.cookbook.thymeleaf.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;

@Slf4j
@NoArgsConstructor
public class CommonUtilController {
    protected static void addProfileToModel(Model model, @AuthenticationPrincipal OidcUser principal){
        log.info("addProfileToModel() - checking principal");
        if (principal != null){
            log.info("addProfileToModel() - got principal with claims: {}", principal.getClaims());
            log.info("addProfileToModel() - adding principal to model");
            model.addAttribute("profile", principal.getClaims());
        }else {
            log.info("addPrincipalToModel() - principal is null");
        }
    }
}
