package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    // Use raw types to avoid generic capture issues
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test findById successfully returns a user")
    void testFindById_Success() {
        // Given
        Long userId = 1L;
        UserResponseDto expectedUser = new UserResponseDto(1L, "john_doe", "John", "Doe", "john.doe@example.com", null, null, "USER");

        // Mocking WebClient behavior
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);  // GET request
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserResponseDto.class)).thenReturn(Mono.just(expectedUser));

        // When
        Mono<UserResponseDto> result = userRepository.findById(userId);

        // Then
        StepVerifier.create(result)
                .expectNext(expectedUser)
                .verifyComplete();

        // Verify the WebClient was called correctly
        verify(webClientBuilder).build();
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(anyString());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(UserResponseDto.class);
    }

    @Test
    @DisplayName("Test findById handles WebClient exception")
    void testFindById_WebClientException() {
        // Given
        Long userId = 1L;
        String errorMessage = "404 Not Found";
        WebClientResponseException webClientResponseException = WebClientResponseException.create(404, errorMessage, null, null, null);

        // Mocking WebClient behavior
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);  // GET request
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserResponseDto.class)).thenReturn(Mono.error(webClientResponseException));

        // When
        Mono<UserResponseDto> result = userRepository.findById(userId);

        // Then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ExternalMicroserviceException &&
                        throwable.getMessage().contains("Error fetching user by ID"))
                .verify();

        // Verify the WebClient was called correctly
        verify(webClientBuilder).build();
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(anyString());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(UserResponseDto.class);
    }

}
