package cz.mavenclu.cookbook.thymeleaf.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class SecurityWebMvcConfig implements WebMvcConfigurer {

    private final SecurityAccessTokenInterceptor accessTokenInterceptor;

    public SecurityWebMvcConfig(SecurityAccessTokenInterceptor accessTokenInterceptor) {
        this.accessTokenInterceptor = accessTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessTokenInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/", "classpath:/static/");
    }
}
