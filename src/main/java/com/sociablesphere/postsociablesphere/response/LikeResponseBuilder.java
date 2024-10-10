package com.sociablesphere.postsociablesphere.response;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class LikeResponseBuilder {

    public static Mono<ResponseEntity<Void>> buildLikeResponse(Long postId, Long userId) {
        return Mono.just(ResponseEntity.created(null).<Void>build());
    }

    public static Mono<ResponseEntity<Void>> buildDislikeResponse(Long postId, Long userId) {
        return Mono.just(ResponseEntity.noContent().<Void>build());
    }

}
