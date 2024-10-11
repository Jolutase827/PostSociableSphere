package com.sociablesphere.postsociablesphere.service.user.impl;


import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.exceptions.InvalidCredentialsException;
import com.sociablesphere.postsociablesphere.repository.UserRepository;
import static com.sociablesphere.postsociablesphere.service.data.DataUserServiceImplTest.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    @DisplayName("Get User Or Throw")
    class GetUserOrThrowTests {
        @Test
        @DisplayName("Given existing user ID, when getUserOrThrow is called, then return UserResponseDto")
        void getUserOrThrowValid() {
            // Given
            when(userRepository.findById(USER_ID)).thenReturn(Mono.just(USER_RESPONSE_DTO));
            // Map USER to UserResponseDto
            UserResponseDto expectedResponse = USER_RESPONSE_DTO;

            // When
            Mono<UserResponseDto> result = userService.getUserOrThrow(USER_ID);

            // Then
            StepVerifier.create(result)
                    .assertNext(userResponseDto -> {
                        assertThat(userResponseDto).isEqualTo(expectedResponse);
                    })
                    .verifyComplete();

            verify(userRepository).findById(USER_ID);
        }

        @Test
        @DisplayName("Given non-existing user ID, when getUserOrThrow is called, then throw InvalidCredentialsException")
        void getUserOrThrowNotFound() {
            // Given
            when(userRepository.findById(USER_ID)).thenReturn(Mono.empty());

            // When
            Mono<UserResponseDto> result = userService.getUserOrThrow(USER_ID);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof InvalidCredentialsException &&
                                    throwable.getMessage().equals("The user with the id " + USER_ID + " does not exist"))
                    .verify();

            verify(userRepository).findById(USER_ID);
        }
    }
}
