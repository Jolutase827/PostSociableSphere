package com.sociablesphere.postsociablesphere.response.builder;

import com.sociablesphere.postsociablesphere.api.dto.PostResponseDto;
import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostResponseBuilder {

    public static ResponseEntity<PostResponseDto> generateCreatedResponse(PostResponseDto post, Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    public static ResponseEntity<Void> generateNoContentResponse() {
        return ResponseEntity.noContent().build();
    }

    public static ResponseEntity<PostResponseDto> generateOkResponse(PostResponseDto post) {
        return ResponseEntity.ok(post);
    }

    public static ResponseEntity<UserResponseDto> generateOwnerAddedResponse(UserResponseDto owner) {
        return ResponseEntity.status(HttpStatus.CREATED).body(owner);
    }
}
