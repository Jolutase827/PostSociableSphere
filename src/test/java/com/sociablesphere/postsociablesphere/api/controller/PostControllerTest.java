package com.sociablesphere.postsociablesphere.api.controller;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.response.logic.PostServiceLogic;
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
    private PostServiceLogic postServiceLogic;

    @InjectMocks
    private PostController postController;

    @Test
    @DisplayName("Given valid PostCreationDto, when createPost is called, then return ResponseEntity with created status")
    void createPostSuccess() {
        // Given
        PostCreationDto postCreationDto = PostCreationDto.builder()
                .content("This is a new post")
                .userId(1L)
                .build();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(1L)
                .content("This is a new post")
                .build();

        when(postServiceLogic.createPostAndBuildResponse(postCreationDto))
                .thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto)));

        // When
        Mono<ResponseEntity<PostResponseDto>> result = postController.createPost(postCreationDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                    assertThat(response.getBody()).isEqualTo(postResponseDto);
                })
                .verifyComplete();

        verify(postServiceLogic).createPostAndBuildResponse(postCreationDto);
    }

    @Test
    @DisplayName("Given valid PostUpdateDto, when updatePost is called, then return ResponseEntity with OK status")
    void updatePostSuccess() {
        // Given
        Long postId = 1L;
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("This is an updated post")
                .build();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(postId)
                .content("This is an updated post")
                .build();

        when(postServiceLogic.updatePostAndBuildResponse(postId, postUpdateDto))
                .thenReturn(Mono.just(ResponseEntity.ok(postResponseDto)));

        // When
        Mono<ResponseEntity<PostResponseDto>> result = postController.updatePost(postId, postUpdateDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isEqualTo(postResponseDto);
                })
                .verifyComplete();

        verify(postServiceLogic).updatePostAndBuildResponse(postId, postUpdateDto);
    }

    @Test
    @DisplayName("Given valid postId, when deletePost is called, then return ResponseEntity with NoContent status")
    void deletePostSuccess() {
        // Given
        Long postId = 1L;

        when(postServiceLogic.deletePostAndBuildResponse(postId))
                .thenReturn(Mono.just(ResponseEntity.noContent().build()));

        // When
        Mono<ResponseEntity<Void>> result = postController.deletePost(postId);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT))
                .verifyComplete();

        verify(postServiceLogic).deletePostAndBuildResponse(postId);
    }

    @Test
    @DisplayName("Given valid NewOwnerDto, when addOwner is called, then return ResponseEntity with created status")
    void addOwnerSuccess() {
        // Given
        NewOwnerDto newOwnerDto = NewOwnerDto.builder()
                .postId(1L)
                .userId(2L)
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(2L)
                .name("Toni")
                .build();

        when(postServiceLogic.addOwnerAndBuildResponse(newOwnerDto))
                .thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto)));

        // When
        Mono<ResponseEntity<UserResponseDto>> result = postController.addOwner(newOwnerDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                    assertThat(response.getBody()).isEqualTo(userResponseDto);
                })
                .verifyComplete();

        verify(postServiceLogic).addOwnerAndBuildResponse(newOwnerDto);
    }
}
