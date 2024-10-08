package com.sociablesphere.postsociablesphere.api.controller;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.response.ResponseBuilder;
import com.sociablesphere.postsociablesphere.service.post.PostService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<PostResponseDto>> createPost(@Valid @RequestBody PostCreationDto postCreationDto) {
        return postService.createPost(postCreationDto)
                .map(post -> ResponseBuilder.buildCreatedResponse(post, postCreationDto.getUserId()))
                .doOnSuccess(post -> logger.info("Post created successfully by user {}", postCreationDto.getUserId()));
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<PostResponseDto>> updatePost(@PathVariable Long id, @Valid @RequestBody PostUpdateDto postUpdateDto) {
        return postService.updatePost(id, postUpdateDto)
                .map(ResponseBuilder::buildOkResponse)
                .doOnSuccess(updatedPost -> logger.info("Post {} updated successfully", id));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deletePost(@PathVariable Long id) {
        return postService.deletePost(id)
                .then(ResponseBuilder.buildNoContentResponse(id))
                .doOnSuccess(updatedPost -> logger.info("Deleted post with id: {}", id));
    }

    @PostMapping("/add-owner")
    public Mono<ResponseEntity<UserResponseDto>> addOwner(@Valid @RequestBody NewOwnerDto newOwnerDto) {
        return postService.addOwner(newOwnerDto)
                .map(ResponseBuilder::buildOwnerAddedResponse)
                .doOnSuccess(owner -> logger.info("User {} added as owner of post {}", newOwnerDto.getUserId(), newOwnerDto.getPostId()));
    }
}
