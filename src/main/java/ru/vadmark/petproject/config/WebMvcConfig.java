package ru.vadmark.petproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${rest.svelte.url}")
    public String clientUrl;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/accessDenied").setViewName("accessDenied");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/svelte/**")
                .allowedMethods("POST", "GET", "DELETE")
                .allowedOrigins(clientUrl);
    }
}
