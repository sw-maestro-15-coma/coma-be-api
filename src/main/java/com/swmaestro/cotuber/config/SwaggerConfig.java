package com.swmaestro.cotuber.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Cotuber API",
                version = "1.0",
                description = "API for Cotuber service"
        )
)
@Configuration
public class SwaggerConfig {
    @Bean
    GroupedOpenApi openApi() {
        return GroupedOpenApi.builder()
                .group("Cotuber API")
                .pathsToMatch("/**")
                .build();
    }
}

