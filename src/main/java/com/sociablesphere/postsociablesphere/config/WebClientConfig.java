package com.sociablesphere.postsociablesphere.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${users.url}")
    private String USERS_URL;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().baseUrl(USERS_URL);
    }
}
