package com.sociablesphere.postsociablesphere.response.logic;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.response.service.PostResponseService;
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
class PostServiceLogicTest {

    @Mock
    private PostService postService;

    @Mock
    private PostResponseService postResponseService;

    @InjectMocks
    private PostServiceLogic postServiceLogic;

    @Test
    @DisplayName("Given valid PostCreationDto, when createPostAndBuildResponse is called, then return ResponseEntity with success status")
    void createPostAndBuildResponseSuccess() {
        // Given
        PostCreationDto postCreationDto = PostCreationDto.builder()
                .userId(1L)
                .content("Test Content")
                .build();
        PostResponseDto postResponseDto = PostResponseDto.builder().build();

        when(postService.createPost(postCreationDto)).thenReturn(Mono.just(postResponseDto));
        when(postResponseService.buildCreatedResponse(postResponseDto, 1L)).thenReturn(Mono.just(ResponseEntity.ok(postResponseDto)));

        // When
        Mono<ResponseEntity<PostResponseDto>> result = postServiceLogic.createPostAndBuildResponse(postCreationDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    // Comprobar el código de estado del ResponseEntity
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isNotNull();
                })
                .verifyComplete();

        verify(postService).createPost(postCreationDto);
        verify(postResponseService).buildCreatedResponse(postResponseDto, 1L);
    }

    @Test
    @DisplayName("Given valid PostUpdateDto, when updatePostAndBuildResponse is called, then return ResponseEntity with success status")
    void updatePostAndBuildResponseSuccess() {
        // Given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder().content("Updated Content").build();
        PostResponseDto postResponseDto = PostResponseDto.builder().build();

        when(postService.updatePost(1L, postUpdateDto)).thenReturn(Mono.just(postResponseDto));
        when(postResponseService.buildOkResponse(postResponseDto)).thenReturn(Mono.just(ResponseEntity.ok(postResponseDto)));

        // When
        Mono<ResponseEntity<PostResponseDto>> result = postServiceLogic.updatePostAndBuildResponse(1L, postUpdateDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    // Comprobar el código de estado del ResponseEntity
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isNotNull();
                })
                .verifyComplete();

        verify(postService).updatePost(1L, postUpdateDto);
        verify(postResponseService).buildOkResponse(postResponseDto);
    }

    @Test
    @DisplayName("Given valid postId, when deletePostAndBuildResponse is called, then return ResponseEntity with no content status")
    void deletePostAndBuildResponseSuccess() {
        // Given
        when(postService.deletePost(1L)).thenReturn(Mono.empty());
        when(postResponseService.buildNoContentResponse()).thenReturn(Mono.just(ResponseEntity.noContent().build()));

        // When
        Mono<ResponseEntity<Void>> result = postServiceLogic.deletePostAndBuildResponse(1L);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    // Comprobar el código de estado del ResponseEntity
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                })
                .verifyComplete();

        verify(postService).deletePost(1L);
        verify(postResponseService).buildNoContentResponse();
    }

    @Test
    @DisplayName("Given valid NewOwnerDto, when addOwnerAndBuildResponse is called, then return ResponseEntity with success status")
    void addOwnerAndBuildResponseSuccess() {
        // Given
        NewOwnerDto newOwnerDto = NewOwnerDto.builder().postId(1L).userId(2L).build();
        UserResponseDto userResponseDto = UserResponseDto.builder().build();

        when(postService.addOwner(newOwnerDto)).thenReturn(Mono.just(userResponseDto));
        when(postResponseService.buildOwnerAddedResponse(userResponseDto)).thenReturn(Mono.just(ResponseEntity.ok(userResponseDto)));

        // When
        Mono<ResponseEntity<UserResponseDto>> result = postServiceLogic.addOwnerAndBuildResponse(newOwnerDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    // Comprobar el código de estado del ResponseEntity
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isNotNull();
                })
                .verifyComplete();

        verify(postService).addOwner(newOwnerDto);
        verify(postResponseService).buildOwnerAddedResponse(userResponseDto);
    }
}
