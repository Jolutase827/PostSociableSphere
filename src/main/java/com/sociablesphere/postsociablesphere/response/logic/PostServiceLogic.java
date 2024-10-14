package com.sociablesphere.postsociablesphere.response.logic;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.response.service.PostResponseService;
import com.sociablesphere.postsociablesphere.service.post.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class PostServiceLogic {

    private final PostService postService;
    private final PostResponseService postResponseService;

    public Mono<ResponseEntity<PostResponseDto>> createPostAndBuildResponse(@Valid PostCreationDto postCreationDto) {
        log.info("Creating post for user ID: {}", postCreationDto.getUserId());
        return postService.createPost(postCreationDto)
                .flatMap(post -> {
                    log.info("Post created successfully with ID: {}", post.getId());
                    return postResponseService.buildCreatedResponse(post, postCreationDto.getUserId());
                });
    }

    public Mono<ResponseEntity<PostResponseDto>> updatePostAndBuildResponse(Long id, @Valid PostUpdateDto postUpdateDto) {
        log.info("Updating post with ID: {}", id);
        return postService.updatePost(id, postUpdateDto)
                .flatMap(postResponseService::buildOkResponse);
    }

    public Mono<ResponseEntity<Void>> deletePostAndBuildResponse(Long id) {
        log.info("Deleting post with ID: {}", id);
        return postService.deletePost(id)
                .then(postResponseService.buildNoContentResponse())
                .doOnSuccess(aVoid -> log.info("Post with ID {} deleted successfully", id));
    }


    public Mono<ResponseEntity<UserResponseDto>> addOwnerAndBuildResponse(@Valid NewOwnerDto newOwnerDto) {
        log.info("Adding owner with user ID: {} to post ID: {}", newOwnerDto.getUserId(), newOwnerDto.getPostId());
        return postService.addOwner(newOwnerDto)
                .flatMap(postResponseService::buildOwnerAddedResponse);
    }
}
