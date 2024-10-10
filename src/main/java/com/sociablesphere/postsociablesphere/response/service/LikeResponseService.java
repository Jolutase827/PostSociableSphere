package com.sociablesphere.postsociablesphere.response.service;

import com.sociablesphere.postsociablesphere.response.builder.LikeResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LikeResponseService {

    public Mono<ResponseEntity<Void>> buildLikeResponse(Long postId, Long userId) {
        return Mono.just(LikeResponseBuilder.buildLikeResponse(postId, userId));
    }

    public Mono<ResponseEntity<Void>> buildDislikeResponse(Long postId, Long userId) {
        return Mono.just(LikeResponseBuilder.buildDislikeResponse(postId, userId));
    }
}
