package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
                .onErrorResume(WebClientResponseException.class, error -> {
                    String errorMessage = String.format("Error fetching user by apiToken: %s", error.getMessage());
                    System.err.println(errorMessage);
                    throw new ExternalMicroserviceException(errorMessage);
                });
    }

    public Mono<UserResponseDto> findById(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri("http://userservice/v1/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserResponseDto.class)
                .onErrorResume(WebClientResponseException.class, error -> {
                    String errorMessage = String.format("Error fetching user by ID: %s", error.getMessage());
                    System.err.println(errorMessage);
                    throw new ExternalMicroserviceException(errorMessage);
                });
    }
}
