package cz.mavenclu.cookbook.thymeleaf.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChefMmvController {


    @GetMapping("/account")
    public String showAccountInfo(Model model, @AuthenticationPrincipal OidcUser principal){
        CommonUtilController.addProfileToModel(model, principal);
        return "account";
    }

}
