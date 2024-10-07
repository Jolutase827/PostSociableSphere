package com.sociablesphere.postsociablesphere.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("post_users")
public class PostUser {

    @Id
    private PostUserId id;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostUserId {
        private Long postId;
        private Long userId;
    }
}
