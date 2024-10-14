package com.sociablesphere.postsociablesphere.api.controller;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.response.logic.PostServiceLogic;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostServiceLogic postServiceLogic;

    @PostMapping("/create")
    public Mono<ResponseEntity<PostResponseDto>> createPost(@Valid @RequestBody PostCreationDto postCreationDto) {
        return postServiceLogic.createPostAndBuildResponse(postCreationDto);
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<PostResponseDto>> updatePost(@PathVariable Long id, @Valid @RequestBody PostUpdateDto postUpdateDto) {
        return postServiceLogic.updatePostAndBuildResponse(id, postUpdateDto);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deletePost(@PathVariable Long id) {
        return postServiceLogic.deletePostAndBuildResponse(id);
    }

    @PostMapping("/add-owner")
    public Mono<ResponseEntity<UserResponseDto>> addOwner(@Valid @RequestBody NewOwnerDto newOwnerDto) {
        return postServiceLogic.addOwnerAndBuildResponse(newOwnerDto);
    }
}

