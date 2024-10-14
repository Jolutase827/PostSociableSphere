package com.sociablesphere.postsociablesphere.api.controller;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.response.logic.LikeServiceLogic;
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
class LikeControllerTest {

    @Mock
    private LikeServiceLogic likeServiceLogic;

    @InjectMocks
    private LikeController likeController;

    @Test
    @DisplayName("Given valid LikeDto, when performLike is called, then return ResponseEntity with success status")
    void performLikeSuccess() {
        // Given
        LikeDto likeDto = LikeDto.builder()
                .postId(1L)
                .userId(2L)
                .build();

        when(likeServiceLogic.performLikeAndBuildResponse(likeDto))
                .thenReturn(Mono.just(ResponseEntity.ok().build()));

        // When
        Mono<ResponseEntity<Void>> result = likeController.performLike(likeDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                })
                .verifyComplete();

        verify(likeServiceLogic).performLikeAndBuildResponse(likeDto);
    }

    @Test
    @DisplayName("Given valid LikeDto, when performDislike is called, then return ResponseEntity with success status")
    void performDislikeSuccess() {
        // Given
        LikeDto likeDto = LikeDto.builder()
                .postId(1L)
                .userId(2L)
                .build();

        when(likeServiceLogic.performDislikeAndBuildResponse(likeDto))
                .thenReturn(Mono.just(ResponseEntity.ok().build()));

        // When
        Mono<ResponseEntity<Void>> result = likeController.performDislike(likeDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                })
                .verifyComplete();

        verify(likeServiceLogic).performDislikeAndBuildResponse(likeDto);
    }
}
