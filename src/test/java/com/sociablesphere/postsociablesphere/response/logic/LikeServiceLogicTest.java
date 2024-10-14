package com.sociablesphere.postsociablesphere.response.logic;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.response.service.LikeResponseService;
import com.sociablesphere.postsociablesphere.service.like.LikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceLogicTest {

    @Mock
    private LikeService likeService;

    @Mock
    private LikeResponseService likeResponseService;

    @InjectMocks
    private LikeServiceLogic likeServiceLogic;

    @Test
    @DisplayName("Given valid LikeDto, when performLikeAndBuildResponse is called, then return ResponseEntity with success status")
    void performLikeAndBuildResponseSuccess() {
        // Given
        LikeDto likeDto = LikeDto.builder()
                .postId(1L)
                .userId(2L)
                .build();

        when(likeService.performLike(likeDto)).thenReturn(Mono.just(1L));
        when(likeResponseService.buildLikeResponse(1L, 2L)).thenReturn(Mono.just(ResponseEntity.ok().build()));

        // When
        Mono<ResponseEntity<Void>> result = likeServiceLogic.performLikeAndBuildResponse(likeDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getStatusCodeValue()).isEqualTo(200);
                })
                .verifyComplete();

        verify(likeService).performLike(likeDto);
        verify(likeResponseService).buildLikeResponse(1L, 2L);
    }

    @Test
    @DisplayName("Given valid LikeDto, when performDislikeAndBuildResponse is called, then return ResponseEntity with success status")
    void performDislikeAndBuildResponseSuccess() {
        // Given
        LikeDto likeDto = LikeDto.builder()
                .postId(1L)
                .userId(2L)
                .build();

        when(likeService.performDislike(likeDto)).thenReturn(Mono.just(1L));
        when(likeResponseService.buildDislikeResponse(1L, 2L)).thenReturn(Mono.just(ResponseEntity.ok().build()));

        // When
        Mono<ResponseEntity<Void>> result = likeServiceLogic.performDislikeAndBuildResponse(likeDto);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getStatusCodeValue()).isEqualTo(200);
                })
                .verifyComplete();

        verify(likeService).performDislike(likeDto);
        verify(likeResponseService).buildDislikeResponse(1L, 2L);
    }
}
