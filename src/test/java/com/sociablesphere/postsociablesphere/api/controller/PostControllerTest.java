package com.sociablesphere.postsociablesphere.api.controller;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.service.post.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    @DisplayName("Given valid PostCreationDto, when createPost is called, then return ResponseEntity with PostResponseDto")
    void createPostSuccess() {
        // Given
        PostCreationDto postCreationDto = PostCreationDto.builder()
                .content("Test Content")
                .type("text")
                .userId(1L)
                .build();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(1L)
                .content("Test Content")
                .build();

        when(postService.createPost(postCreationDto)).thenReturn(Mono.just(postResponseDto));

        // When
        Mono<ResponseEntity<PostResponseDto>> result = postController.createPost(postCreationDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                    assertThat(response.getBody()).isEqualTo(postResponseDto);
                })
                .verifyComplete();

        verify(postService).createPost(postCreationDto);
    }

    @Test
    @DisplayName("Given valid PostUpdateDto, when updatePost is called, then return ResponseEntity with updated PostResponseDto")
    void updatePostSuccess() {
        // Given
        Long postId = 1L;
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("Updated Content")
                .build();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(postId)
                .content("Updated Content")
                .build();

        when(postService.updatePost(postId, postUpdateDto)).thenReturn(Mono.just(postResponseDto));

        // When
        Mono<ResponseEntity<PostResponseDto>> result = postController.updatePost(postId, postUpdateDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isEqualTo(postResponseDto);
                })
                .verifyComplete();

        verify(postService).updatePost(postId, postUpdateDto);
    }

    @Test
    @DisplayName("Given postId, when deletePost is called, then return ResponseEntity with no content")
    void deletePostSuccess() {
        // Given
        Long postId = 1L;

        when(postService.deletePost(postId)).thenReturn(Mono.empty());

        // When
        Mono<ResponseEntity<Void>> result = postController.deletePost(postId);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT))
                .verifyComplete();

        verify(postService).deletePost(postId);
    }

    @Test
    @DisplayName("Given valid NewOwnerDto, when addOwner is called, then return ResponseEntity with UserResponseDto")
    void addOwnerSuccess() {
        // Given
        NewOwnerDto newOwnerDto = NewOwnerDto.builder()
                .postId(1L)
                .userId(2L)
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(2L)
                .userName("newowner")
                .build();

        when(postService.addOwner(newOwnerDto)).thenReturn(Mono.just(userResponseDto));

        // When
        Mono<ResponseEntity<UserResponseDto>> result = postController.addOwner(newOwnerDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                    assertThat(response.getBody()).isEqualTo(userResponseDto);
                })
                .verifyComplete();

        verify(postService).addOwner(newOwnerDto);
    }


}

