package cz.mavenclu.cookbook.thymeleaf.security;

import cz.mavenclu.cookbook.thymeleaf.exception.PrincipalNotFoundException;
import cz.mavenclu.cookbook.thymeleaf.exception.UnAuthenticatedClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class SecurityAccessTokenInterceptor implements HandlerInterceptor {

    private final OAuth2AuthorizedClientService oauth2ClientService;


    public SecurityAccessTokenInterceptor(OAuth2AuthorizedClientService oauth2ClientService) {
        this.oauth2ClientService = oauth2ClientService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getRequestURI().equals("/logout")) {



        try {
            log.info("preHandle - looking for principal");
            String principalName = getPrincipalName();
            if (oauth2ClientService.loadAuthorizedClient("auth0", principalName) == null){
                throw new UnAuthenticatedClientException();
            }
            OAuth2AccessToken accessToken = oauth2ClientService.loadAuthorizedClient("auth0", principalName).getAccessToken();
            if (accessToken != null) {
                injectAccessToken(request, accessToken.getTokenValue());
                response.setStatus(200);
                return true;
            } else {
                log.info("preHandle - access token is missing. returning false.");
                response.setStatus(401);
            }

        } catch (PrincipalNotFoundException e) {
            log.warn("Principal not found. Unauthorized request. Returning false");
            response.setStatus(401);
        } catch (UnAuthenticatedClientException unAuthenticatedClientException) {
            log.warn("preHandle - client is not authenticated. returning false.");
            response.setStatus(401);
        }

        return false;
        }
        return true;

    }

    private String getPrincipalName() throws PrincipalNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        } else {
            throw new PrincipalNotFoundException();
        }
    }

    private void injectAccessToken(HttpServletRequest request, String accessToken) {
        log.info("injectAccessToken - injecting access token");
        request.setAttribute("accessToken", accessToken);
    }

}
