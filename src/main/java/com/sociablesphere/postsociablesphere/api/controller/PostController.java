package com.sociablesphere.postsociablesphere.api.controller;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.response.service.PostResponseService;
import com.sociablesphere.postsociablesphere.service.post.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/posts")
@AllArgsConstructor
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final PostResponseService postResponseService;


    @PostMapping("/create")
    public Mono<ResponseEntity<PostResponseDto>> createPost(@Valid @RequestBody PostCreationDto postCreationDto) {
        logger.info("Creating POST with user ID: {}", postCreationDto.getUserId());
        return postService.createPost(postCreationDto)
                .flatMap(post -> postResponseService.buildCreatedResponse(post, postCreationDto.getUserId()))
                .doOnSuccess(post -> logger.info("Post created successfully by user {}", postCreationDto.getUserId()));
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<PostResponseDto>> updatePost(@PathVariable Long id, @Valid @RequestBody PostUpdateDto postUpdateDto) {
        logger.info("Updating POST with user ID: {}", id);
        return postService.updatePost(id, postUpdateDto)
                .flatMap(postResponseService::buildOkResponse)
                .doOnSuccess(updatedPost -> logger.info("Post {} updated successfully", id));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deletePost(@PathVariable Long id) {
        logger.info("Deleting POST with user ID: {}", id);
        return postService.deletePost(id)
                .then(postResponseService.buildNoContentResponse())
                .doOnSuccess(updatedPost -> logger.info("Deleted post with id: {}", id));
    }

    @PostMapping("/add-owner")
    public Mono<ResponseEntity<UserResponseDto>> addOwner(@Valid @RequestBody NewOwnerDto newOwnerDto) {
        logger.info("Adding new owner with ID: {}, to POST: {}", newOwnerDto.getUserId(), newOwnerDto.getPostId());
        return postService.addOwner(newOwnerDto)
                .flatMap(postResponseService::buildOwnerAddedResponse)
                .doOnSuccess(owner -> logger.info("User {} added as owner of post {}", newOwnerDto.getUserId(), newOwnerDto.getPostId()));
    }
}
