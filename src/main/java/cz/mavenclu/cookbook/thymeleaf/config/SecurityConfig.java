package cz.mavenclu.cookbook.thymeleaf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final LogoutHandler logoutHandler;

    public SecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/", "/recipes/**", "/css/**", "/fonts/**", "/img/**", "/js/**", "/scss/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/recipes/add").authenticated() //todo zmenit cestu , jelikoz /recipes/1
                .anyRequest().authenticated()
                .and().oauth2Login()
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .addLogoutHandler(logoutHandler);
    }

}