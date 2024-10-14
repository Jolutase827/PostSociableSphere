package com.sociablesphere.postsociablesphere.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleExternalMicroserviceException_ShouldReturnInternalServerError() {
        // Arrange
        ExternalMicroserviceException ex = new ExternalMicroserviceException("Microservice error");
        WebRequest webRequest = mock(WebRequest.class);

        // Act
        Mono<ResponseEntity<ErrorResponse>> response = globalExceptionHandler.handleExternalMicroserviceException(ex, webRequest);

        // Assert
        StepVerifier.create(response)
                .assertNext(entity -> {
                    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
                    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), entity.getBody().getCode());
                    assertEquals("Microservice error", entity.getBody().getMessage());
                })
                .verifyComplete();
    }

    @Test
    void handleInvalidCredentialsException_ShouldReturnUnauthorized() {
        // Arrange
        InvalidCredentialsException ex = new InvalidCredentialsException("Invalid credentials");
        WebRequest webRequest = mock(WebRequest.class);

        // Act
        Mono<ResponseEntity<ErrorResponse>> response = globalExceptionHandler.handleInvalidCredentialsException(ex, webRequest);

        // Assert
        StepVerifier.create(response)
                .assertNext(entity -> {
                    assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), entity.getBody().getCode());
                    assertEquals("Invalid credentials", entity.getBody().getMessage());
                })
                .verifyComplete();
    }
}
