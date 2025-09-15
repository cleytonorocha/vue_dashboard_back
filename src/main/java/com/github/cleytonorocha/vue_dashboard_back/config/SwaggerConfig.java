package com.github.cleytonorocha.vue_dashboard_back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;

@Configuration
@OpenAPIDefinition
@RequiredArgsConstructor
public class SwaggerConfig {

    @Value("${spring.application.version}")
    private String versionApp;

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("back_dashboard")
                        .version(versionApp)
                        .description(
                                "The aim of developing this application is to provide a backend dashboard for managing various interconnected products."));
    }
}
