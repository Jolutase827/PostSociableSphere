package com.sociablesphere.postsociablesphere.service.post;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import com.sociablesphere.postsociablesphere.exceptions.InvalidCredentialsException;
import com.sociablesphere.postsociablesphere.mapper.PostMapper;
import com.sociablesphere.postsociablesphere.model.Post;
import com.sociablesphere.postsociablesphere.model.PostUser;
import com.sociablesphere.postsociablesphere.repository.LikeRepository;
import com.sociablesphere.postsociablesphere.repository.PostRepository;
import com.sociablesphere.postsociablesphere.repository.PostUserRepository;
import com.sociablesphere.postsociablesphere.repository.UserRepository;
import com.sociablesphere.postsociablesphere.service.post.impl.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostUserRepository postUserRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Nested
    @DisplayName("Create Post")
    class CreatePost {

        @Test
        @DisplayName("Given valid PostCreationDto, when user exists, then create post and return PostResponseDto")
        void createPostSuccess() {
            // Given
            PostCreationDto postCreationDto = PostCreationDto.builder()
                    .content("Test Content")
                    .type("text")
                    .isPaid(false)
                    .cost(BigDecimal.ZERO)
                    .isAd(false)
                    .maxViews(100)
                    .footer("Test Footer")
                    .parentId(null)
                    .userId(1L)
                    .build();

            UserResponseDto userResponseDto = UserResponseDto.builder()
                    .id(1L)
                    .userName("testuser")
                    .name("Test")
                    .lastName("User")
                    .email("testuser@example.com")
                    .photo("photo.jpg")
                    .description("Test User Description")
                    .role("USER")
                    .build();

            Post savedPost = PostMapper.toEntity(postCreationDto);
            savedPost.setId(1L);
            savedPost.setCreatedAt(LocalDateTime.now());
            savedPost.setUpdatedAt(LocalDateTime.now());

            PostUser savedPostUser = new PostUser(
                    new PostUser.PostUserId(savedPost.getId(), userResponseDto.getId())
            );

            when(userRepository.findById(postCreationDto.getUserId())).thenReturn(Mono.just(userResponseDto));
            when(postRepository.save(any(Post.class))).thenReturn(Mono.just(savedPost));
            when(postUserRepository.save(any(PostUser.class))).thenReturn(Mono.just(savedPostUser));
            when(likeRepository.findByIdPostId(savedPost.getId())).thenReturn(Flux.empty());
            when(postUserRepository.findByIdPostId(savedPost.getId())).thenReturn(Flux.just(savedPostUser));
            when(userRepository.findById(savedPostUser.getId().getUserId())).thenReturn(Mono.just(userResponseDto));

            // When
            Mono<PostResponseDto> result = postService.createPost(postCreationDto);

            // Then
            StepVerifier.create(result)
                    .assertNext(postResponseDto -> {
                        assertThat(postResponseDto).isNotNull();
                        assertThat(postResponseDto.getId()).isEqualTo(savedPost.getId());
                        assertThat(postResponseDto.getContent()).isEqualTo(savedPost.getContent());
                        assertThat(postResponseDto.getPostOwners()).containsExactly(userResponseDto);
                        assertThat(postResponseDto.getLikes()).isEmpty();
                    })
                    .verifyComplete();

            // Verify interactions
            verify(userRepository,times(2)).findById(postCreationDto.getUserId());
            verify(postRepository).save(any(Post.class));
            verify(postUserRepository).save(any(PostUser.class));
            verify(likeRepository).findByIdPostId(savedPost.getId());
            verify(postUserRepository).findByIdPostId(savedPost.getId());
        }

        @Test
        @DisplayName("Given PostCreationDto, when user does not exist, then throw InvalidCredentialsException")
        void createPostUserNotFound() {
            // Given
            PostCreationDto postCreationDto = PostCreationDto.builder()
                    .userId(1L)
                    .build();

            when(userRepository.findById(postCreationDto.getUserId())).thenReturn(Mono.empty());

            // When
            Mono<PostResponseDto> result = postService.createPost(postCreationDto);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(error -> error instanceof ExternalMicroserviceException &&
                            error.getMessage().equals("Error al crear el post: Usuario no encontrado"))
                    .verify();


            // Verify interactions
            verify(userRepository).findById(postCreationDto.getUserId());
            verifyNoMoreInteractions(postRepository, postUserRepository, likeRepository);
        }
    }

    @Nested
    @DisplayName("Update Post")
    class UpdatePost {

        @Test
        @DisplayName("Given valid PostUpdateDto, when post exists, then update post and return PostResponseDto")
        void updatePostSuccess() {
            // Given
            Long postId = 1L;
            PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                    .content("Updated Content")
                    .build();

            Post existingPost = Post.builder()
                    .id(postId)
                    .content("Original Content")
                    .build();

            Post updatedPost = Post.builder()
                    .id(postId)
                    .content(postUpdateDto.getContent())
                    .updatedAt(LocalDateTime.now())
                    .build();

            when(postRepository.findById(postId)).thenReturn(Mono.just(existingPost));
            when(postRepository.save(any(Post.class))).thenReturn(Mono.just(updatedPost));
            when(likeRepository.findByIdPostId(postId)).thenReturn(Flux.empty());
            when(postUserRepository.findByIdPostId(postId)).thenReturn(Flux.empty());

            // When
            Mono<PostResponseDto> result = postService.updatePost(postId, postUpdateDto);

            // Then
            StepVerifier.create(result)
                    .assertNext(postResponseDto -> {
                        assertThat(postResponseDto).isNotNull();
                        assertThat(postResponseDto.getId()).isEqualTo(postId);
                        assertThat(postResponseDto.getContent()).isEqualTo(postUpdateDto.getContent());
                    })
                    .verifyComplete();

            // Verify interactions
            verify(postRepository).findById(postId);
            verify(postRepository).save(any(Post.class));
            verify(likeRepository).findByIdPostId(postId);
            verify(postUserRepository).findByIdPostId(postId);
        }

        @Test
        @DisplayName("Given PostUpdateDto, when post does not exist, then throw ExternalMicroserviceException")
        void updatePostNotFound() {
            // Given
            Long postId = 1L;
            PostUpdateDto postUpdateDto = PostUpdateDto.builder().build();

            when(postRepository.findById(postId)).thenReturn(Mono.empty());

            // When
            Mono<PostResponseDto> result = postService.updatePost(postId, postUpdateDto);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(error -> error instanceof ExternalMicroserviceException &&
                            error.getMessage().equals("Post no encontrado"))
                    .verify();

            // Verify interactions
            verify(postRepository).findById(postId);
            verifyNoMoreInteractions(postRepository);
        }
    }

    @Nested
    @DisplayName("Delete Post")
    class DeletePost {

        @Test
        @DisplayName("Given existing postId, when post exists, then delete post and related data")
        void deletePostSuccess() {
            // Given
            Long postId = 1L;

            when(postRepository.existsById(postId)).thenReturn(Mono.just(true));
            when(postUserRepository.deleteByIdPostId(postId)).thenReturn(Mono.empty());
            when(likeRepository.deleteByIdPostId(postId)).thenReturn(Mono.empty());
            when(postRepository.deleteById(postId)).thenReturn(Mono.empty());

            // When
            Mono<Void> result = postService.deletePost(postId);

            // Then
            StepVerifier.create(result)
                    .verifyComplete();

            // Verify interactions
            verify(postRepository).existsById(postId);
            verify(postUserRepository).deleteByIdPostId(postId);
            verify(likeRepository).deleteByIdPostId(postId);
            verify(postRepository).deleteById(postId);
        }

        @Test
        @DisplayName("Given postId, when post does not exist, then throw ExternalMicroserviceException")
        void deletePostNotFound() {
            // Given
            Long postId = 1L;

            when(postRepository.existsById(postId)).thenReturn(Mono.just(false));

            // When
            Mono<Void> result = postService.deletePost(postId);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(error -> error instanceof ExternalMicroserviceException &&
                            error.getMessage().equals("Post no encontrado"))
                    .verify();

            // Verify interactions
            verify(postRepository).existsById(postId);
            verifyNoMoreInteractions(postUserRepository, likeRepository, postRepository);
        }
    }

    @Nested
    @DisplayName("Add Owner")
    class AddOwner {

        @Test
        @DisplayName("Given NewOwnerDto, when post and user exist, then add owner and return UserResponseDto")
        void addOwnerSuccess() {
            // Given
            NewOwnerDto newOwnerDto = NewOwnerDto.builder()
                    .postId(1L)
                    .userId(2L)
                    .build();

            Post post = Post.builder()
                    .id(newOwnerDto.getPostId())
                    .build();

            UserResponseDto userResponseDto = UserResponseDto.builder()
                    .id(newOwnerDto.getUserId())
                    .userName("newowner")
                    .build();

            PostUser postUser = new PostUser(
                    new PostUser.PostUserId(post.getId(), userResponseDto.getId())
            );

            when(postRepository.findById(newOwnerDto.getPostId())).thenReturn(Mono.just(post));
            when(userRepository.findById(newOwnerDto.getUserId())).thenReturn(Mono.just(userResponseDto));
            when(postUserRepository.save(any(PostUser.class))).thenReturn(Mono.just(postUser));

            // When
            Mono<UserResponseDto> result = postService.addOwner(newOwnerDto);

            // Then
            StepVerifier.create(result)
                    .assertNext(responseDto -> {
                        assertThat(responseDto).isNotNull();
                        assertThat(responseDto.getId()).isEqualTo(userResponseDto.getId());
                        assertThat(responseDto.getUserName()).isEqualTo(userResponseDto.getUserName());
                    })
                    .verifyComplete();

            // Verify interactions
            verify(postRepository).findById(newOwnerDto.getPostId());
            verify(userRepository).findById(newOwnerDto.getUserId());
            verify(postUserRepository).save(any(PostUser.class));
        }

        @Test
        @DisplayName("Given NewOwnerDto, when post does not exist, then throw ExternalMicroserviceException")
        void addOwnerPostNotFound() {
            // Given
            NewOwnerDto newOwnerDto = NewOwnerDto.builder()
                    .postId(1L)
                    .userId(2L)
                    .build();

            when(postRepository.findById(newOwnerDto.getPostId())).thenReturn(Mono.empty());

            // When
            Mono<UserResponseDto> result = postService.addOwner(newOwnerDto);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(error -> error instanceof ExternalMicroserviceException &&
                            error.getMessage().equals("Error al agregar propietario: Post no encontrado"))
                    .verify();

            // Verify interactions
            verify(postRepository).findById(newOwnerDto.getPostId());
            verifyNoMoreInteractions(userRepository, postUserRepository);
        }

        @Test
        @DisplayName("Given NewOwnerDto, when user does not exist, then throw InvalidCredentialsException")
        void addOwnerUserNotFound() {
            // Given
            NewOwnerDto newOwnerDto = NewOwnerDto.builder()
                    .postId(1L)
                    .userId(2L)
                    .build();

            Post post = Post.builder()
                    .id(newOwnerDto.getPostId())
                    .build();

            when(postRepository.findById(newOwnerDto.getPostId())).thenReturn(Mono.just(post));
            when(userRepository.findById(newOwnerDto.getUserId())).thenReturn(Mono.empty());

            // When
            Mono<UserResponseDto> result = postService.addOwner(newOwnerDto);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(error -> error instanceof ExternalMicroserviceException &&
                            error.getMessage().equals("Error al agregar propietario: Usuario no encontrado"))
                    .verify();


            // Verify interactions
            verify(postRepository).findById(newOwnerDto.getPostId());
            verify(userRepository).findById(newOwnerDto.getUserId());
            verifyNoMoreInteractions(postUserRepository);
        }

    }
}
