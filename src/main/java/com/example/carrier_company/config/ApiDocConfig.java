package com.example.carrier_company.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApiDocConfig {
    private Info apiInfo() {
        return new Info().title("Carrier company API")
                .description("Api for managing deliveries")
                .version("0.1.0")
                .contact(new Contact().name("Roman Bylo").email("bylo.roman@gmail.com"));
    }

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI().info(apiInfo());
    }
}
