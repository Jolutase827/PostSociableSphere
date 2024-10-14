package com.sociablesphere.postsociablesphere.service.like.impl;

import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import com.sociablesphere.postsociablesphere.repository.LikeRepository;
import com.sociablesphere.postsociablesphere.repository.PostRepository;
import static com.sociablesphere.postsociablesphere.service.data.DataLikeServiceImplTest.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Set;
import static com.sociablesphere.postsociablesphere.service.data.DataLikeServiceImplTest.LIKE_RESPONSE_DTO;
import static com.sociablesphere.postsociablesphere.service.data.DataLikeServiceImplTest.POST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LikeServiceImplTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    @Nested
    @DisplayName("Perform Like")
    class PerformLikeTests {
        @Test
        @DisplayName("Given a valid LikeDto and existing Post, when performLike is called, then Like is saved and postId is returned")
        void performLikeValid() {
            // Given
            when(postRepository.findById(POST_ID)).thenReturn(Mono.just(POST));
            when(likeRepository.existsById(LIKE_ID)).thenReturn(Mono.just(false));
            when(likeRepository.save(any())).thenReturn(Mono.just(LIKE));

            // When
            Mono<Long> result = likeService.performLike(LIKE_DTO);

            // Then
            StepVerifier.create(result)
                    .expectNext(POST_ID)
                    .verifyComplete();

            verify(postRepository).findById(POST_ID);
            verify(likeRepository).existsById(LIKE_ID);
            verify(likeRepository).save(any());
        }

        @Test
        @DisplayName("Given a LikeDto with non-existing Post, when performLike is called, then ExternalMicroserviceException is thrown")
        void performLikePostNotFound() {
            // Given
            when(postRepository.findById(POST_ID)).thenReturn(Mono.empty());

            // When
            Mono<Long> result = likeService.performLike(LIKE_DTO);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof ExternalMicroserviceException &&
                                    throwable.getMessage().equals("The post with the id " + POST_ID + " does not exist"))
                    .verify();

            verify(postRepository).findById(POST_ID);
        }

        @Test
        @DisplayName("Given a LikeDto where Like already exists, when performLike is called, then ExternalMicroserviceException is thrown")
        void performLikeAlreadyLiked() {
            // Given
            when(postRepository.findById(POST_ID)).thenReturn(Mono.just(POST));
            when(likeRepository.existsById(LIKE_ID)).thenReturn(Mono.just(true));

            // When
            Mono<Long> result = likeService.performLike(LIKE_DTO);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof ExternalMicroserviceException &&
                                    throwable.getMessage().equals("The user with the id " + USER_ID + " already liked the post"))
                    .verify();

            verify(postRepository).findById(POST_ID);
            verify(likeRepository).existsById(LIKE_ID);
        }
    }

    @Nested
    @DisplayName("Perform Dislike")
    class PerformDislikeTests {
        @Test
        @DisplayName("Given a valid LikeDto where Like exists, when performDislike is called, then Like is deleted and postId is returned")
        void performDislikeValid() {
            // Given
            when(likeRepository.findById(LIKE_ID)).thenReturn(Mono.just(LIKE));
            when(likeRepository.delete(LIKE)).thenReturn(Mono.empty());

            // When
            Mono<Long> result = likeService.performDislike(LIKE_DTO);

            // Then
            StepVerifier.create(result)
                    .expectNext(POST_ID)
                    .verifyComplete();

            verify(likeRepository).findById(LIKE_ID);
            verify(likeRepository).delete(LIKE);
        }

        @Test
        @DisplayName("Given a LikeDto where Like does not exist, when performDislike is called, then ExternalMicroserviceException is thrown")
        void performDislikeNotLiked() {
            // Given
            when(likeRepository.findById(LIKE_ID)).thenReturn(Mono.empty());

            // When
            Mono<Long> result = likeService.performDislike(LIKE_DTO);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof ExternalMicroserviceException &&
                                    throwable.getMessage().equals("The user with the id " + USER_ID + " didn't like the post already"))
                    .verify();

            verify(likeRepository).findById(LIKE_ID);
        }
    }

    @Nested
    @DisplayName("Get Likes By Post ID")
    class GetLikesByPostIdTests {
        @Test
        @DisplayName("Given a valid postId, when getLikesByPostId is called, then return set of LikeResponseDto")
        void getLikesByPostIdValid() {
            // Given
            when(likeRepository.findByPostId(POST_ID)).thenReturn(Flux.just(LIKE));

            // When
            Mono<Set<LikeResponseDto>> result = likeService.getLikesByPostId(POST_ID);

            // Then
            StepVerifier.create(result)
                    .assertNext(likes -> {
                        assertThat(likes).contains(LIKE_RESPONSE_DTO);
                    })
                    .verifyComplete();

            verify(likeRepository).findByPostId(POST_ID);
        }
    }

    @Nested
    @DisplayName("Delete All Likes By Post ID")
    class DeleteAllLikesByPostIdTests {
        @Test
        @DisplayName("Given a valid postId, when deleteAllLikesByPostId is called, then likes are deleted")
        void deleteAllLikesByPostIdValid() {
            // Given
            when(likeRepository.deleteByPostId(POST_ID)).thenReturn(Mono.empty());

            // When
            Mono<Void> result = likeService.deleteAllLikesByPostId(POST_ID);

            // Then
            StepVerifier.create(result)
                    .verifyComplete();

            verify(likeRepository).deleteByPostId(POST_ID);
        }
    }
}