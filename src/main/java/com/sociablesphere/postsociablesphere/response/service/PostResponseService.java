package com.sociablesphere.postsociablesphere.response.service;

import com.sociablesphere.postsociablesphere.api.dto.PostResponseDto;
import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.response.builder.PostResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PostResponseService {

    public Mono<ResponseEntity<PostResponseDto>> buildCreatedResponse(PostResponseDto post, Long userId) {
        return Mono.fromCallable(() -> PostResponseBuilder.generateCreatedResponse(post, userId));
    }

    public Mono<ResponseEntity<PostResponseDto>> buildOkResponse(PostResponseDto post) {
        return Mono.fromCallable(() -> PostResponseBuilder.generateOkResponse(post));
    }

    public Mono<ResponseEntity<Void>> buildNoContentResponse() {
        return Mono.just(PostResponseBuilder.generateNoContentResponse());
    }

    public Mono<ResponseEntity<UserResponseDto>> buildOwnerAddedResponse(UserResponseDto user) {
        return Mono.fromCallable(() -> PostResponseBuilder.generateOwnerAddedResponse(user));
    }
}
