package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.api.dto.UserDetailDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class UserRepository {

    private final WebClient.Builder webClientBuilder;

    public UserRepository(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<UserDetailDTO> findByApiToken(String apiToken) {
        return webClientBuilder.build()
                .get()
                .uri("http://userservice/v1/users/apiToken?apiToken={apiToken}", apiToken)
                .retrieve()
                .bodyToMono(UserDetailDTO.class)
                .onErrorResume(error -> {
                    System.err.println("Error fetching user by apiToken: " + error.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<UserDetailDTO> findById(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri("http://userservice/v1/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserDetailDTO.class)
                .onErrorResume(error -> {
                    System.err.println("Error fetching user by ID: " + error.getMessage());
                    return Mono.empty();
                });
    }
}
