package com.sociablesphere.postsociablesphere.mapper;


import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.model.Like;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class LikeMapperTest {

    @Nested
    @DisplayName("toEntity Tests")
    class ToEntityTests {

        @Test
        @DisplayName("Given a valid LikeDto, when toEntity is called, then return a Like entity with correct values")
        void toEntity_ValidDto_ReturnsEntity() {
            // Given
            LikeDto likeDto = LikeDto.builder()
                    .userId(1L)
                    .postId(2L)
                    .build();

            LocalDateTime beforeMapping = LocalDateTime.now();

            // When
            Like like = LikeMapper.toEntity(likeDto);

            // Then
            assertThat(like).isNotNull();
            assertThat(like.getId()).isNotNull();
            assertThat(like.getId().getUserId()).isEqualTo(likeDto.getPostId()); // Note the mapping
            assertThat(like.getId().getPostId()).isEqualTo(likeDto.getUserId()); // Note the mapping
            assertThat(like.getCreatedAt()).isAfterOrEqualTo(beforeMapping);
        }
    }

    @Nested
    @DisplayName("toResponseDto Tests")
    class ToResponseDtoTests {

        @Test
        @DisplayName("Given a valid Like entity, when toResponseDto is called, then return a LikeResponseDto with correct values")
        void toResponseDto_ValidEntity_ReturnsDto() {
            // Given
            Like.LikeId likeId = Like.LikeId.builder()
                    .userId(2L)
                    .postId(1L)
                    .build();

            LocalDateTime createdAt = LocalDateTime.now();

            Like like = Like.builder()
                    .id(likeId)
                    .createdAt(createdAt)
                    .build();

            // When
            LikeResponseDto likeResponseDto = LikeMapper.toResponseDto(like);

            // Then
            assertThat(likeResponseDto).isNotNull();
            assertThat(likeResponseDto.getUserId()).isEqualTo(like.getId().getUserId());
            assertThat(likeResponseDto.getCreatedAt()).isAfterOrEqualTo(createdAt);
        }
    }
}
