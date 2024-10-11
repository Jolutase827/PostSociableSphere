package com.sociablesphere.postsociablesphere.mapper;


import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.model.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PostMapperTest {

    @Nested
    @DisplayName("toEntity")
    class ToEntityTests {
        @Test
        @DisplayName("Given a PostCreationDto, when toEntity is called, then return Post entity")
        void toEntityValid() {
            // Given
            PostCreationDto dto = PostCreationDto.builder()
                    .content("Test Content")
                    .type("text")
                    .isPaid(true)
                    .cost(BigDecimal.TEN)
                    .isAd(false)
                    .maxViews(100)
                    .footer("Test Footer")
                    .parentId(1L)
                    .build();

            // When
            Post post = PostMapper.toEntity(dto);

            // Then
            assertThat(post.getContent()).isEqualTo("Test Content");
            assertThat(post.getType()).isEqualTo("text");
            assertThat(post.getIsPaid()).isTrue();
            assertThat(post.getCost()).isEqualByComparingTo(BigDecimal.TEN);
            assertThat(post.getIsAd()).isFalse();
            assertThat(post.getMaxViews()).isEqualTo(100);
            assertThat(post.getViewsRemaining()).isEqualTo(0);
            assertThat(post.getFooter()).isEqualTo("Test Footer");
            assertThat(post.getParentId()).isEqualTo(1L);
            assertThat(post.getCreatedAt()).isNotNull();
            assertThat(post.getUpdatedAt()).isNotNull();
        }
    }

    @Nested
    @DisplayName("toDto")
    class ToDtoTests {
        @Test
        @DisplayName("Given a Post entity, when toDto is called, then return PostResponseDto")
        void toDtoValid() {
            // Given
            Post post = Post.builder()
                    .id(1L)
                    .content("Test Content")
                    .type("text")
                    .isPaid(true)
                    .cost(BigDecimal.TEN)
                    .isAd(false)
                    .maxViews(100)
                    .viewsRemaining(50)
                    .footer("Test Footer")
                    .parentId(1L)
                    .build();

            // When
            PostResponseDto dto = PostMapper.toDto(post);

            // Then
            assertThat(dto.getId()).isEqualTo(1L);
            assertThat(dto.getContent()).isEqualTo("Test Content");
            assertThat(dto.getType()).isEqualTo("text");
            assertThat(dto.getIsPaid()).isTrue();
            assertThat(dto.getCost()).isEqualByComparingTo(BigDecimal.TEN);
            assertThat(dto.getIsAd()).isFalse();
            assertThat(dto.getMaxViews()).isEqualTo(100);
            assertThat(dto.getViewsRemaining()).isEqualTo(50);
            assertThat(dto.getFooter()).isEqualTo("Test Footer");
            assertThat(dto.getParentId()).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("toDto with likes and owners")
    class ToDtoWithLikesAndOwnersTests {
        @Test
        @DisplayName("Given a Post entity and likes and owners, when toDto is called, then return PostResponseDto with likes and owners")
        void toDtoWithLikesAndOwnersValid() {
            // Given
            Post post = Post.builder()
                    .id(1L)
                    .content("Test Content")
                    .type("text")
                    .isPaid(true)
                    .cost(BigDecimal.TEN)
                    .isAd(false)
                    .maxViews(100)
                    .viewsRemaining(50)
                    .footer("Test Footer")
                    .parentId(1L)
                    .build();

            Set<LikeResponseDto> likes = new HashSet<>();
            likes.add(LikeResponseDto.builder().userId(2L).createdAt(LocalDateTime.now()).build());

            Set<UserResponseDto> owners = new HashSet<>();
            owners.add(UserResponseDto.builder().id(3L).userName("Owner").build());

            // When
            PostResponseDto dto = PostMapper.toDto(post, likes, owners);

            // Then
            assertThat(dto.getLikes()).isEqualTo(likes);
            assertThat(dto.getPostOwners()).isEqualTo(owners);
        }
    }

    @Nested
    @DisplayName("updateEntityFromDto")
    class UpdateEntityFromDtoTests {
        @Test
        @DisplayName("Given a Post entity and PostUpdateDto, when updateEntityFromDto is called, then Post entity is updated")
        void updateEntityFromDtoValid() {
            // Given
            Post post = Post.builder()
                    .content("Old Content")
                    .isPaid(false)
                    .cost(BigDecimal.ZERO)
                    .isAd(false)
                    .maxViews(100)
                    .footer("Old Footer")
                    .build();

            PostUpdateDto dto = PostUpdateDto.builder()
                    .content("New Content")
                    .isPaid(true)
                    .cost(BigDecimal.TEN)
                    .isAd(true)
                    .maxViews(200)
                    .footer("New Footer")
                    .build();

            // When
            PostMapper.updateEntityFromDto(post, dto);

            // Then
            assertThat(post.getContent()).isEqualTo("New Content");
            assertThat(post.getIsPaid()).isTrue();
            assertThat(post.getCost()).isEqualByComparingTo(BigDecimal.TEN);
            assertThat(post.getIsAd()).isTrue();
            assertThat(post.getMaxViews()).isEqualTo(200);
            assertThat(post.getFooter()).isEqualTo("New Footer");
            assertThat(post.getUpdatedAt()).isNotNull();
        }
    }

    @Nested
    @DisplayName("zipPostLikesUsersToPostResponse")
    class ZipPostLikesUsersToPostResponseTests {
        @Test
        @DisplayName("Given a Post entity and Tuple2 of likes and owners, when zipPostLikesUsersToPostResponse is called, then return PostResponseDto")
        void zipPostLikesUsersToPostResponseValid() {
            // Given
            Post post = Post.builder()
                    .id(1L)
                    .content("Test Content")
                    .build();

            Set<LikeResponseDto> likes = new HashSet<>();
            likes.add(LikeResponseDto.builder().userId(2L).createdAt(LocalDateTime.now()).build());

            Set<UserResponseDto> owners = new HashSet<>();
            owners.add(UserResponseDto.builder().id(3L).userName("Owner").build());

            Tuple2<Set<LikeResponseDto>, Set<UserResponseDto>> tuple = Tuples.of(likes, owners);

            // When
            PostResponseDto dto = PostMapper.zipPostLikesUsersToPostResponse(post, tuple);

            // Then
            assertThat(dto.getId()).isEqualTo(1L);
            assertThat(dto.getLikes()).isEqualTo(likes);
            assertThat(dto.getPostOwners()).isEqualTo(owners);
        }
    }
}