package com.sociablesphere.postsociablesphere.service.like;


import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import com.sociablesphere.postsociablesphere.model.Like;
import com.sociablesphere.postsociablesphere.model.Post;
import com.sociablesphere.postsociablesphere.repository.LikeRepository;
import com.sociablesphere.postsociablesphere.repository.PostRepository;
import com.sociablesphere.postsociablesphere.service.like.impl.LikeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    @Nested
    @DisplayName("Perform Like")
    class PerformLike {

        @Test
        @DisplayName("Given valid LikeDto, when post exists and like does not exist, then create like and return postId")
        void performLikeSuccess() {
            // Given
            LikeDto likeDto = LikeDto.builder()
                    .userId(1L)
                    .postId(1L)
                    .build();

            Post post = Post.builder()
                    .id(likeDto.getPostId())
                    .build();

            Like.LikeId likeId = Like.LikeId.builder()
                    .userId(likeDto.getUserId())
                    .postId(likeDto.getPostId())
                    .build();

            Like like = Like.builder()
                    .id(likeId)
                    .createdAt(LocalDateTime.now())
                    .build();

            when(postRepository.findById(likeDto.getPostId())).thenReturn(Mono.just(post));
            when(likeRepository.existsById(likeId)).thenReturn(Mono.just(false));
            when(likeRepository.save(any(Like.class))).thenReturn(Mono.just(like));

            // When
            Mono<Long> result = likeService.performLike(likeDto);

            // Then
            StepVerifier.create(result)
                    .expectNext(likeDto.getPostId())
                    .verifyComplete();

            // Verify interactions
            verify(postRepository).findById(likeDto.getPostId());
            verify(likeRepository).existsById(likeId);
            verify(likeRepository).save(any(Like.class));
        }

        @Test
        @DisplayName("Given LikeDto, when post does not exist, then throw ExternalMicroserviceException")
        void performLikePostNotFound() {
            // Given
            LikeDto likeDto = LikeDto.builder()
                    .userId(1L)
                    .postId(1L)
                    .build();

            when(postRepository.findById(likeDto.getPostId())).thenReturn(Mono.empty());

            // When
            Mono<Long> result = likeService.performLike(likeDto);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(error -> error instanceof ExternalMicroserviceException &&
                            error.getMessage().equals("Post no encontrado"))
                    .verify();

            // Verify interactions
            verify(postRepository).findById(likeDto.getPostId());
            verifyNoMoreInteractions(likeRepository);
        }

        @Test
        @DisplayName("Given LikeDto, when like already exists, then throw ExternalMicroserviceException")
        void performLikeAlreadyLiked() {
            // Given
            LikeDto likeDto = LikeDto.builder()
                    .userId(1L)
                    .postId(1L)
                    .build();

            Post post = Post.builder()
                    .id(likeDto.getPostId())
                    .build();

            Like.LikeId likeId = Like.LikeId.builder()
                    .userId(likeDto.getUserId())
                    .postId(likeDto.getPostId())
                    .build();

            when(postRepository.findById(likeDto.getPostId())).thenReturn(Mono.just(post));
            when(likeRepository.existsById(likeId)).thenReturn(Mono.just(true));

            // When
            Mono<Long> result = likeService.performLike(likeDto);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(error -> error instanceof ExternalMicroserviceException &&
                            error.getMessage().equals("El usuario ya ha dado like a este post"))
                    .verify();

            // Verify interactions
            verify(postRepository).findById(likeDto.getPostId());
            verify(likeRepository).existsById(likeId);
            verifyNoMoreInteractions(likeRepository);
        }
    }

    @Nested
    @DisplayName("Perform Dislike")
    class PerformDislike {

        @Test
        @DisplayName("Given valid LikeDto, when like exists, then delete like and return postId")
        void performDislikeSuccess() {
            // Given
            LikeDto likeDto = LikeDto.builder()
                    .userId(1L)
                    .postId(1L)
                    .build();

            Like.LikeId likeId = Like.LikeId.builder()
                    .userId(likeDto.getUserId())
                    .postId(likeDto.getPostId())
                    .build();

            Like like = Like.builder()
                    .id(likeId)
                    .createdAt(LocalDateTime.now())
                    .build();

            when(likeRepository.findById(likeId)).thenReturn(Mono.just(like));
            when(likeRepository.delete(like)).thenReturn(Mono.empty());

            // When
            Mono<Long> result = likeService.performDislike(likeDto);

            // Then
            StepVerifier.create(result)
                    .expectNext(likeDto.getPostId())
                    .verifyComplete();

            // Verify interactions
            verify(likeRepository).findById(likeId);
            verify(likeRepository).delete(like);
        }

        @Test
        @DisplayName("Given LikeDto, when like does not exist, then throw ExternalMicroserviceException")
        void performDislikeLikeNotFound() {
            // Given
            LikeDto likeDto = LikeDto.builder()
                    .userId(1L)
                    .postId(1L)
                    .build();

            Like.LikeId likeId = Like.LikeId.builder()
                    .userId(likeDto.getUserId())
                    .postId(likeDto.getPostId())
                    .build();

            when(likeRepository.findById(likeId)).thenReturn(Mono.empty());

            // When
            Mono<Long> result = likeService.performDislike(likeDto);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(error -> error instanceof ExternalMicroserviceException &&
                            error.getMessage().equals("Like no encontrado"))
                    .verify();

            // Verify interactions
            verify(likeRepository).findById(likeId);
            verifyNoMoreInteractions(likeRepository);
        }
    }
}
