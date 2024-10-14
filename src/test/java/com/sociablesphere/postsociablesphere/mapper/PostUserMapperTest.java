package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.model.PostUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PostUserMapperTest {

    @Test
    @DisplayName("Given postId and userId, when toEntity is called, then return PostUser entity")
    void toEntityValid() {
        // Given
        Long postId = 1L;
        Long userId = 2L;

        // When
        PostUser postUser = PostUserMapper.toEntity(postId, userId);

        // Then
        assertThat(postUser.getPostId()).isEqualTo(postId);
        assertThat(postUser.getUserId()).isEqualTo(userId);
    }
}
