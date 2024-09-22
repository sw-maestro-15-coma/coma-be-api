package com.swmaestro.cotuber.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Cotuber API",
                version = "1.0",
                description = "API for Cotuber service"
        ),
        servers = {
                @Server(
                        url = "https://api.cotuber.com",
                        description = "Production server"
                ),
                @Server(
                        url = "http://localhost:8080",
                        description = "Local server"
                )
        }
)
@Configuration
public class SwaggerConfig {
    @Bean
    OpenAPI openApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(components());
    }

    Components components() {
        return new Components()
                .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme());
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}

