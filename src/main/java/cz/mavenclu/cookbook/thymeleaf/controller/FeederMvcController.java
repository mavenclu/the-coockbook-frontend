package cz.mavenclu.cookbook.thymeleaf.controller;

import cz.mavenclu.cookbook.thymeleaf.dto.FeederForm;
import cz.mavenclu.cookbook.thymeleaf.service.FeederService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/feeders")
public class FeederMvcController {

    private final FeederService feederService;

    public FeederMvcController(FeederService feederService) {
        this.feederService = feederService;
    }

    @GetMapping("/consumers")
    public String showConsumers(Model model, @RequestAttribute("accessToken") String accessToken){
        model.addAttribute("feeders", feederService.findAll(accessToken));
        return "feeders";
    }

    @GetMapping("/add")
    public String renderFeedersForm(Model model, @AuthenticationPrincipal OidcUser principal){
        model.addAttribute("principal", principal);
        model.addAttribute("feeder", new FeederForm());
        return "add-feeder";

    }

    @PostMapping("/save")
    public String saveFeeder(@ModelAttribute FeederForm feeder, Model model, BindingResult bindingResult, @RequestAttribute("accessToken") String accessToken) {
        log.info("saveFeeder - params feeder: {}", feeder);
        if(bindingResult.hasErrors()) {
            log.warn("saveFeeder - found errors in model: {}", bindingResult.getAllErrors());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/feeders/add";
        }
        feederService.saveFeeder(feeder, accessToken);
        return "redirect:/feeders/consumers";
    }
}
