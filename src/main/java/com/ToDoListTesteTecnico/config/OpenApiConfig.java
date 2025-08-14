package com.ToDoListTesteTecnico.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
public class OpenApiConfig {

    private static final String TITLE = "To Do List";
    private static final String VERSION = "V1.0";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(TITLE)
                        .version(VERSION)
                );
    }
}
