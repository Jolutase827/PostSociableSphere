package com.sociablesphere.postsociablesphere.mapper;


import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.model.Likes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class LikesMapperTest {

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
            Likes likes = LikeMapper.toEntity(likeDto);

            // Then
            assertThat(likes).isNotNull();
            assertThat(likes.getId()).isNotNull();
            assertThat(likes.getId().getUserId()).isEqualTo(likeDto.getPostId()); // Note the mapping
            assertThat(likes.getId().getPostId()).isEqualTo(likeDto.getUserId()); // Note the mapping
            assertThat(likes.getCreatedAt()).isAfterOrEqualTo(beforeMapping);
        }
    }

    @Nested
    @DisplayName("toResponseDto Tests")
    class ToResponseDtoTests {

        @Test
        @DisplayName("Given a valid Like entity, when toResponseDto is called, then return a LikeResponseDto with correct values")
        void toResponseDto_ValidEntity_ReturnsDto() {
            // Given
            Likes.LikeId likeId = Likes.LikeId.builder()
                    .userId(2L)
                    .postId(1L)
                    .build();

            LocalDateTime createdAt = LocalDateTime.now();

            Likes likes = Likes.builder()
                    .id(likeId)
                    .createdAt(createdAt)
                    .build();

            // When
            LikeResponseDto likeResponseDto = LikeMapper.toResponseDto(likes);

            // Then
            assertThat(likeResponseDto).isNotNull();
            assertThat(likeResponseDto.getUserId()).isEqualTo(likes.getId().getUserId());
            assertThat(likeResponseDto.getCreatedAt()).isAfterOrEqualTo(createdAt);
        }
    }
}
