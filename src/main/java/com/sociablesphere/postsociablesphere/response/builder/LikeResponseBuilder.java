package com.sociablesphere.postsociablesphere.response.builder;

import org.springframework.http.ResponseEntity;

public class LikeResponseBuilder {

    public static ResponseEntity<Void> buildLikeResponse(Long postId, Long userId) {
        return ResponseEntity.created(null).<Void>build();
    }

    public static ResponseEntity<Void> buildDislikeResponse(Long postId, Long userId) {
        return ResponseEntity.noContent().<Void>build();
    }
}
