package com.sociablesphere.postsociablesphere.mapper;


import com.sociablesphere.postsociablesphere.api.dto.PostCreationDto;
import com.sociablesphere.postsociablesphere.api.dto.PostResponseDto;
import com.sociablesphere.postsociablesphere.api.dto.PostUpdateDto;
import com.sociablesphere.postsociablesphere.model.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class PostMapperTest {

    @Nested
    @DisplayName("toEntity Tests")
    class ToEntityTests {

        @Test
        @DisplayName("Given a valid PostCreationDto, when toEntity is called, then return a Post entity with correct values")
        void toEntity_ValidDto_ReturnsEntity() {
            // Given
            PostCreationDto dto = PostCreationDto.builder()
                    .content("Test Content")
                    .type("text")
                    .isPaid(true)
                    .cost(BigDecimal.valueOf(9.99))
                    .isAd(false)
                    .maxViews(100)
                    .footer("Test Footer")
                    .parentId(null)
                    .build();

            LocalDateTime beforeMapping = LocalDateTime.now();

            // When
            Post post = PostMapper.toEntity(dto);

            // Then
            assertThat(post).isNotNull();
            assertThat(post.getContent()).isEqualTo(dto.getContent());
            assertThat(post.getType()).isEqualTo(dto.getType());
            assertThat(post.getIsPaid()).isEqualTo(dto.getIsPaid());
            assertThat(post.getCost()).isEqualByComparingTo(dto.getCost());
            assertThat(post.getIsAd()).isEqualTo(dto.getIsAd());
            assertThat(post.getMaxViews()).isEqualTo(dto.getMaxViews());
            assertThat(post.getViewsRemaining()).isEqualTo(dto.getMaxViews());
            assertThat(post.getFooter()).isEqualTo(dto.getFooter());
            assertThat(post.getParentId()).isEqualTo(dto.getParentId());
            assertThat(post.getCreatedAt()).isAfterOrEqualTo(beforeMapping);
            assertThat(post.getUpdatedAt()).isAfterOrEqualTo(beforeMapping);
        }
    }

    @Nested
    @DisplayName("toDto Tests")
    class ToDtoTests {

        @Test
        @DisplayName("Given a valid Post entity, when toDto is called, then return a PostResponseDto with correct values")
        void toDto_ValidEntity_ReturnsDto() {
            // Given
            Post post = Post.builder()
                    .id(1L)
                    .content("Test Content")
                    .type("text")
                    .isPaid(true)
                    .cost(BigDecimal.valueOf(9.99))
                    .isAd(false)
                    .maxViews(100)
                    .viewsRemaining(80)
                    .footer("Test Footer")
                    .parentId(null)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            // When
            PostResponseDto dto = PostMapper.toDto(post);

            // Then
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isEqualTo(post.getId());
            assertThat(dto.getContent()).isEqualTo(post.getContent());
            assertThat(dto.getType()).isEqualTo(post.getType());
            assertThat(dto.getIsPaid()).isEqualTo(post.getIsPaid());
            assertThat(dto.getCost()).isEqualByComparingTo(post.getCost());
            assertThat(dto.getIsAd()).isEqualTo(post.getIsAd());
            assertThat(dto.getMaxViews()).isEqualTo(post.getMaxViews());
            assertThat(dto.getViewsRemaining()).isEqualTo(post.getViewsRemaining());
            assertThat(dto.getFooter()).isEqualTo(post.getFooter());
            assertThat(dto.getParentId()).isEqualTo(post.getParentId());
        }
    }

    @Nested
    @DisplayName("updateEntityFromDto Tests")
    class UpdateEntityFromDtoTests {

        @Test
        @DisplayName("Given a Post entity and PostUpdateDto with new values, when updateEntityFromDto is called, then entity is updated")
        void updateEntityFromDto_ValidDto_UpdatesEntity() {
            // Given
            Post post = Post.builder()
                    .id(1L)
                    .content("Old Content")
                    .isPaid(false)
                    .cost(BigDecimal.ZERO)
                    .isAd(false)
                    .maxViews(50)
                    .footer("Old Footer")
                    .updatedAt(LocalDateTime.now())
                    .build();

            PostUpdateDto dto = PostUpdateDto.builder()
                    .content("New Content")
                    .isPaid(true)
                    .cost(BigDecimal.valueOf(19.99))
                    .isAd(true)
                    .maxViews(150)
                    .footer("New Footer")
                    .build();

            LocalDateTime beforeUpdate = post.getUpdatedAt();

            // When
            PostMapper.updateEntityFromDto(post, dto);

            // Then
            assertThat(post.getContent()).isEqualTo(dto.getContent());
            assertThat(post.getIsPaid()).isEqualTo(dto.getIsPaid());
            assertThat(post.getCost()).isEqualByComparingTo(dto.getCost());
            assertThat(post.getIsAd()).isEqualTo(dto.getIsAd());
            assertThat(post.getMaxViews()).isEqualTo(dto.getMaxViews());
            assertThat(post.getFooter()).isEqualTo(dto.getFooter());
            assertThat(post.getUpdatedAt()).isAfter(beforeUpdate);
        }

        @Test
        @DisplayName("Given a Post entity and PostUpdateDto with null values, when updateEntityFromDto is called, then entity is unchanged")
        void updateEntityFromDto_NullValues_NoChanges() {
            // Given
            Post post = Post.builder()
                    .id(1L)
                    .content("Original Content")
                    .isPaid(false)
                    .cost(BigDecimal.ZERO)
                    .isAd(false)
                    .maxViews(50)
                    .footer("Original Footer")
                    .updatedAt(LocalDateTime.now())
                    .build();

            PostUpdateDto dto = PostUpdateDto.builder().build();

            LocalDateTime beforeUpdate = post.getUpdatedAt();

            // When
            PostMapper.updateEntityFromDto(post, dto);

            // Then
            assertThat(post.getContent()).isEqualTo("Original Content");
            assertThat(post.getIsPaid()).isEqualTo(false);
            assertThat(post.getCost()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(post.getIsAd()).isEqualTo(false);
            assertThat(post.getMaxViews()).isEqualTo(50);
            assertThat(post.getFooter()).isEqualTo("Original Footer");
            assertThat(post.getUpdatedAt()).isAfterOrEqualTo(beforeUpdate);
        }
    }
}
