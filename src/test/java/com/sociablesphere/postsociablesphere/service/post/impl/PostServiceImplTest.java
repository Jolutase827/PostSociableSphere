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
            when(userService.getUserOrThrow(USER_ID)).thenReturn(Mono.just(USER_RESPONSE_DTO));
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
}

