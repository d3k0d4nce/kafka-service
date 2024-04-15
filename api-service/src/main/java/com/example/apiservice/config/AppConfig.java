package com.example.apiservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient webDataClient() {
        return WebClient.builder().baseUrl("http://localhost:8080").build();
    }

}
