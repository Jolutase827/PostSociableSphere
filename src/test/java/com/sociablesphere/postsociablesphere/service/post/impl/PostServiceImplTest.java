package com.sociablesphere.postsociablesphere.service.post.impl;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import com.sociablesphere.postsociablesphere.model.PostUser;
import static com.sociablesphere.postsociablesphere.service.data.DataPostServiceImplTest.*;
import com.sociablesphere.postsociablesphere.repository.PostRepository;
import com.sociablesphere.postsociablesphere.service.like.LikeService;
import com.sociablesphere.postsociablesphere.service.postuser.PostUserService;
import com.sociablesphere.postsociablesphere.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostUserService postUserService;

    @Mock
    private UserService userService;

    @Mock
    private LikeService likeService;

    @InjectMocks
    private PostServiceImpl postService;

    @Nested
    @DisplayName("Create Post")
    class CreatePostTests {
        @Test
        @DisplayName("Given a valid PostCreationDto and existing User, when createPost is called, then Post is saved and PostResponseDto is returned")
        void createPostValid() {
            // Given
            when(userService.getUserOrThrow(any())).thenReturn(Mono.just(USER_RESPONSE_DTO));
            when(postRepository.save(any())).thenReturn(Mono.just(POST));
            when(postUserService.savePostUser(any(), any())).thenReturn(Mono.just(PostUser.builder().build()));
            when(likeService.getLikesByPostId(POST_ID)).thenReturn(Mono.just(new HashSet<>()));
            when(postUserService.findAllPostUserByPostId(POST_ID)).thenReturn(Flux.empty());

            // When
            Mono<PostResponseDto> result = postService.createPost(POST_CREATION_DTO);

            // Then
            StepVerifier.create(result)
                    .assertNext(postResponseDto -> {
                        assertThat(postResponseDto.getId()).isEqualTo(POST_ID);
                    })
                    .verifyComplete();

            verify(userService).getUserOrThrow(USER_ID);
            verify(postRepository).save(any());
            verify(postUserService).savePostUser(any(), any());
        }
    }

    @Nested
    @DisplayName("Update Post")
    class UpdatePostTests {
        @Test
        @DisplayName("Given an existing Post, when updatePost is called, then Post is updated and PostResponseDto is returned")
        void updatePostValid() {
            // Given
            when(postRepository.findById(POST_ID)).thenReturn(Mono.just(POST));
            when(postRepository.save(any())).thenReturn(Mono.just(POST));
            when(likeService.getLikesByPostId(POST_ID)).thenReturn(Mono.just(new HashSet<>()));
            when(postUserService.findAllPostUserByPostId(POST_ID)).thenReturn(Flux.empty());

            // When
            Mono<PostResponseDto> result = postService.updatePost(POST_ID, POST_UPDATE_DTO);

            // Then
            StepVerifier.create(result)
                    .assertNext(postResponseDto -> {
                        assertThat(postResponseDto.getContent()).isEqualTo(POST_UPDATE_DTO.getContent());
                    })
                    .verifyComplete();

            verify(postRepository).findById(POST_ID);
            verify(postRepository).save(any());
        }

        @Test
        @DisplayName("Given a non-existing Post, when updatePost is called, then ExternalMicroserviceException is thrown")
        void updatePostNotFound() {
            // Given
            when(postRepository.findById(POST_ID)).thenReturn(Mono.empty());

            // When
            Mono<PostResponseDto> result = postService.updatePost(POST_ID, POST_UPDATE_DTO);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof ExternalMicroserviceException &&
                                    throwable.getMessage().equals("The post with the id " + POST_ID + " not exist"))
                    .verify();

            verify(postRepository).findById(POST_ID);
        }
    }

    @Nested
    @DisplayName("Delete Post")
    class DeletePostTests {
        @Test
        @DisplayName("Given an existing Post ID, when deletePost is called, then Post and related data are deleted")
        void deletePostValid() {
            // Given
            when(postRepository.existsById(POST_ID)).thenReturn(Mono.just(true));
            when(postUserService.deleteAllPostUserByPostId(POST_ID)).thenReturn(Mono.empty());
            when(likeService.deleteAllLikesByPostId(POST_ID)).thenReturn(Mono.empty());
            when(postRepository.deleteById(POST_ID)).thenReturn(Mono.empty());

            // When
            Mono<Void> result = postService.deletePost(POST_ID);

            // Then
            StepVerifier.create(result)
                    .verifyComplete();

            verify(postRepository).existsById(POST_ID);
            verify(postUserService).deleteAllPostUserByPostId(POST_ID);
            verify(likeService).deleteAllLikesByPostId(POST_ID);
            verify(postRepository).deleteById(POST_ID);
        }

        @Test
        @DisplayName("Given a non-existing Post ID, when deletePost is called, then ExternalMicroserviceException is thrown")
        void deletePostNotFound() {
            // Given
            when(postRepository.existsById(POST_ID)).thenReturn(Mono.just(false));

            // When
            Mono<Void> result = postService.deletePost(POST_ID);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof ExternalMicroserviceException &&
                                    throwable.getMessage().equals("The post with the id " + POST_ID + " not exist"))
                    .verify();

            verify(postRepository).existsById(POST_ID);
        }
    }

    @Nested
    @DisplayName("Add Owner")
    class AddOwnerTests {

        @Test
        @DisplayName("Given a valid NewOwnerDto, when addOwner is called, the owner is added and UserResponseDto is returned")
        void addOwnerValid() {
            Long postId = 123L;
            Long userId = 456L;
            NewOwnerDto newOwnerDto = new NewOwnerDto(postId, userId);

            UserResponseDto userResponseDto = UserResponseDto.builder()
                    .id(userId)
                    .userName("user123")
                    .name("John")
                    .lastName("Doe")
                    .email("user@example.com")
                    .photo("photo_url")
                    .description("Test description")
                    .role("Admin")
                    .build();

            when(userService.getUserOrThrow(userId)).thenReturn(Mono.just(userResponseDto));
            when(postUserService.savePostUser(postId, userId)).thenReturn(Mono.empty());

            Mono<UserResponseDto> result = postService.addOwner(newOwnerDto);

            StepVerifier.create(result)
                    .assertNext(response -> {
                        // Verifica que el UserResponseDto retornado tiene todos los campos esperados
                        assertThat(response.getId()).isEqualTo(userId);
                        assertThat(response.getUserName()).isEqualTo("user123");
                        assertThat(response.getName()).isEqualTo("John");
                        assertThat(response.getLastName()).isEqualTo("Doe");
                        assertThat(response.getEmail()).isEqualTo("user@example.com");
                        assertThat(response.getPhoto()).isEqualTo("photo_url");
                        assertThat(response.getDescription()).isEqualTo("Test description");
                        assertThat(response.getRole()).isEqualTo("Admin");
                    })
                    .verifyComplete();

            verify(userService).getUserOrThrow(userId);
            verify(postUserService).savePostUser(postId, userId);
        }

        @Test
        @DisplayName("Given a failure in userService, when addOwner is called, an error is returned")
        void addOwnerUserServiceError() {
            Long postId = 1L;
            Long userId = 1L;
            NewOwnerDto newOwnerDto = new NewOwnerDto(postId, userId);

            when(userService.getUserOrThrow(userId))
                    .thenReturn(Mono.error(new RuntimeException("User service failed")));

            Mono<UserResponseDto> result = postService.addOwner(newOwnerDto);

            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof RuntimeException &&
                                    throwable.getMessage().equals("User service failed"))
                    .verify();

            verify(userService).getUserOrThrow(userId);
            verify(postUserService, never()).savePostUser(anyLong(), anyLong());
        }

        @Test
        @DisplayName("Given a failure in postUserService, when addOwner is called, an error is returned")
        void addOwnerPostUserServiceError() {
            Long postId = 123L;
            Long userId = 456L;
            NewOwnerDto newOwnerDto = new NewOwnerDto(postId, userId);

            UserResponseDto userResponseDto = UserResponseDto.builder()
                    .id(userId)
                    .userName("user123")
                    .name("John")
                    .lastName("Doe")
                    .email("user@example.com")
                    .photo("photo_url")
                    .description("Test description")
                    .role("Admin")
                    .build();

            when(userService.getUserOrThrow(userId)).thenReturn(Mono.just(userResponseDto));
            when(postUserService.savePostUser(postId, userId)).thenReturn(Mono.error(new RuntimeException("PostUser service failed")));

            Mono<UserResponseDto> result = postService.addOwner(newOwnerDto);

            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof RuntimeException &&
                                    throwable.getMessage().equals("PostUser service failed"))
                    .verify();

            verify(userService).getUserOrThrow(userId);
            verify(postUserService).savePostUser(postId, userId);
        }
    }
}

