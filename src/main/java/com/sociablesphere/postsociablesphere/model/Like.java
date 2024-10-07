package com.sociablesphere.postsociablesphere.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("likes")
public class Like {

    @Id
    private LikeId id;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LikeId {
        private Long postId;
        private Long userId;
    }
}
