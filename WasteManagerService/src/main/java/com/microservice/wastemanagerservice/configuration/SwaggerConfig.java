package com.microservice.wastemanagerservice.configuration;

//import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("spring")
                .packagesToScan("com.microservice.wastemanagerservice.controller")
                .build();
    }
}
