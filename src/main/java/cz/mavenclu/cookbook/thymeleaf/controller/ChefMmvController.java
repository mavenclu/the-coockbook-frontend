package cz.mavenclu.cookbook.thymeleaf.controller;

import cz.mavenclu.cookbook.thymeleaf.util.ControllerModelPopulateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Slf4j
@Controller
public class ChefMmvController {


    @GetMapping("/account")
    public String showAccountInfo(Model model, @AuthenticationPrincipal OidcUser principal){
        principal.getClaims().forEach((key, val) -> log.info("PRINCIPAL CLAIMS: {} - {}", key, val));
        log.info("PRINCIPAL ID TOKEN: {}", principal.getIdToken().getTokenValue());
        ControllerModelPopulateHelper.addProfileToModel(model, principal);
        return "account";
    }

}
