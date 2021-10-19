package cz.mavenclu.cookbook.thymeleaf.util;

import cz.mavenclu.cookbook.thymeleaf.dto.RecipeItemWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.RecipeWebDto;
import cz.mavenclu.cookbook.thymeleaf.dto.SearchObjectForm;
import cz.mavenclu.cookbook.thymeleaf.service.FeederService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Slf4j
public class ControllerModelPopulateHelper {

    public static void addProfileToModel(Model model, @AuthenticationPrincipal OidcUser principal) {
        log.info("addProfileToModel() - checking principal");
        if (principal != null) {
            log.info("addProfileToModel() - got principal with claims: {}", principal.getClaims());
            log.info("addProfileToModel() - adding principal to model");
            model.addAttribute("profile", principal.getClaims());
        } else {
            log.info("addPrincipalToModel() - principal is null");
        }
    }

    public String getChefId(OidcUser principal){
        return principal.getClaims().get("sub").toString().substring(6);
    }
}
