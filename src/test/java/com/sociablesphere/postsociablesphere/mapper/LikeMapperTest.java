package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.model.Like;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LikeMapperTest {

    @Nested
    @DisplayName("toEntity")
    class ToEntityTests {
        @Test
        @DisplayName("Given a LikeDto, when toEntity is called, then return Like entity")
        void toEntityValid() {
            // Given
            LikeDto likeDto = LikeDto.builder()
                    .userId(1L)
                    .postId(2L)
                    .build();

            // When
            Like like = LikeMapper.toEntity(likeDto);

            // Then
            assertThat(like.getId().getUserId()).isEqualTo(2L);
            assertThat(like.getId().getPostId()).isEqualTo(1L);
            assertThat(like.getCreatedAt()).isNotNull();
        }
    }

    @Nested
    @DisplayName("toResponseDto")
    class ToResponseDtoTests {
        @Test
        @DisplayName("Given a Like entity, when toResponseDto is called, then return LikeResponseDto")
        void toResponseDtoValid() {
            // Given
            Like.LikeId likeId = Like.LikeId.builder()
                    .userId(1L)
                    .postId(2L)
                    .build();
            LocalDateTime createdAt = LocalDateTime.now();
            Like like = Like.builder()
                    .id(likeId)
                    .createdAt(createdAt)
                    .build();

            // When
            LikeResponseDto responseDto = LikeMapper.toResponseDto(like);

            // Then
            assertThat(responseDto.getUserId()).isEqualTo(1L);
            assertThat(responseDto.getCreatedAt()).isEqualTo(createdAt);
        }
    }

    @Nested
    @DisplayName("createLikeId")
    class CreateLikeIdTests {
        @Test
        @DisplayName("Given a LikeDto, when createLikeId is called, then return LikeId")
        void createLikeIdValid() {
            // Given
            LikeDto likeDto = LikeDto.builder()
                    .userId(1L)
                    .postId(2L)
                    .build();

            // When
            Like.LikeId likeId = LikeMapper.createLikeId(likeDto);

            // Then
            assertThat(likeId.getUserId()).isEqualTo(1L);
            assertThat(likeId.getPostId()).isEqualTo(2L);
        }
    }
}