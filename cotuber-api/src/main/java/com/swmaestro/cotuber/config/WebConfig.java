package com.swmaestro.cotuber.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        "http://localhost:3000",
                        "http://localhost:8080",
                        "https://localhost:3000",
                        "https://localhost:8080",
                        "https://cotuber.com",
                        "https://develop.cotuber.com",
                        "https://api.cotuber.com"
                )
                .allowCredentials(true)
                .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "OPTION",
                        "DELETE"
                );
    }
}
