package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class UserRepository {

    private final WebClient.Builder webClientBuilder;

    private final String ROUTE_GET_USER_BY_ID;
    private final String ROUTE_GET_USERS_BY_IDS;

    public UserRepository(WebClient.Builder webClientBuilder,
                          @Value("${users.get-by-id}") String routeById,
                          @Value("${users.get-by-ids}") String routeByIds) {
        this.ROUTE_GET_USER_BY_ID = routeById;
        this.ROUTE_GET_USERS_BY_IDS = routeByIds;
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<UserResponseDto> findById(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri(ROUTE_GET_USER_BY_ID + userId)
                .retrieve()
                .bodyToMono(UserResponseDto.class)
                .onErrorResume(WebClientResponseException.class, error -> {
                    String errorMessage = String.format("Error fetching user by ID: %s", error.getMessage());
                    System.err.println(errorMessage);
                    throw new ExternalMicroserviceException(errorMessage);
                });
    }

    public Flux<UserResponseDto> findUsersByIds(List<Long> userIds) {
        return webClientBuilder.build()
                .post()
                .uri(ROUTE_GET_USERS_BY_IDS)
                .bodyValue(userIds) // Pass the list of user IDs in the request body
                .retrieve()
                .bodyToFlux(UserResponseDto.class)
                .onErrorResume(WebClientResponseException.class, error -> {
                    String errorMessage = String.format("Error fetching users by IDs: %s", error.getMessage());
                    System.err.println(errorMessage);
                    throw new ExternalMicroserviceException(errorMessage);
                });
    }
}
