package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.model.Likes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LikesMapperTest {

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
            Likes likes = LikeMapper.toEntity(likeDto);

            // Then
            assertThat(likes).isNotNull();
            assertThat(likes.getId()).isNull();
            assertThat(likes.getUserId()).isEqualTo(likeDto.getUserId());
            assertThat(likes.getPostId()).isEqualTo(likeDto.getPostId());
        }
    }

    @Nested
    @DisplayName("toResponseDto")
    class ToResponseDtoTests {
        @Test
        @DisplayName("Given a Like entity, when toResponseDto is called, then return LikeResponseDto")
        void toResponseDtoValid() {
            // Given

            LocalDateTime createdAt = LocalDateTime.now();

            Likes likes = Likes.builder()
                    .id(1L)
                    .userId(2L)
                    .postId(1L)
                    .createdAt(createdAt)
                    .build();

            // When
            LikeResponseDto likeResponseDto = LikeMapper.toResponseDto(likes);

            // Then
            assertThat(likeResponseDto).isNotNull();
            assertThat(likeResponseDto.getUserId()).isEqualTo(likes.getUserId());
            assertThat(likeResponseDto.getCreatedAt()).isAfterOrEqualTo(createdAt);
            LikeResponseDto responseDto = LikeMapper.toResponseDto(likes);

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
            Likes likeId = LikeMapper.toEntity(likeDto);

            // Then
            assertThat(likeId.getUserId()).isEqualTo(1L);
            assertThat(likeId.getPostId()).isEqualTo(2L);
        }
    }
}