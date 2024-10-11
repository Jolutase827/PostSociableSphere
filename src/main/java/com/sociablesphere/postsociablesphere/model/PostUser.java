package com.sociablesphere.postsociablesphere.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUser {

    @Id
    private PostUserId id;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostUserId {
        private Long postId;
        private Long userId;
    }
}
