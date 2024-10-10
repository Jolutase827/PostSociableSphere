package com.sociablesphere.postsociablesphere.response;

import com.sociablesphere.postsociablesphere.api.dto.PostResponseDto;
import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class PostResponseBuilder {

    public static ResponseEntity<PostResponseDto> buildCreatedResponse(PostResponseDto post, Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    public static Mono<ResponseEntity<Void>> buildNoContentResponse(Long postId) {
        return Mono.just(ResponseEntity.noContent().<Void>build());
    }

    public static ResponseEntity<PostResponseDto> buildOkResponse(PostResponseDto post) {
        return ResponseEntity.ok(post);
    }

    public static ResponseEntity<UserResponseDto> buildOwnerAddedResponse(UserResponseDto owner) {
        return ResponseEntity.status(HttpStatus.CREATED).body(owner);
    }

}
