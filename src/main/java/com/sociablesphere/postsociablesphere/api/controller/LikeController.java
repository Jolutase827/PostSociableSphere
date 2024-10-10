package com.sociablesphere.postsociablesphere.api.controller;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.response.LikeResponseBuilder;
import com.sociablesphere.postsociablesphere.service.like.LikeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/likes")
public class LikeController {

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public Mono<ResponseEntity<Void>> performLike(@Valid @RequestBody LikeDto likeDto) {
        return likeService.performLike(likeDto)
                .flatMap(likesCount -> LikeResponseBuilder.buildLikeResponse(likeDto.getPostId(), likeDto.getUserId()))
                .doOnSuccess(response -> logger.info("Post {} liked successfully by user {}", likeDto.getPostId(), likeDto.getUserId()));
    }

    @PostMapping("/dislike")
    public Mono<ResponseEntity<Void>> performDislike(@Valid @RequestBody LikeDto likeDto) {
        return likeService.performDislike(likeDto)
                .flatMap(dislikesCount -> LikeResponseBuilder.buildDislikeResponse(likeDto.getPostId(), likeDto.getUserId()))
                .doOnSuccess(response -> logger.info("Post {} disliked successfully by user {}", likeDto.getPostId(), likeDto.getUserId()));
    }

}
