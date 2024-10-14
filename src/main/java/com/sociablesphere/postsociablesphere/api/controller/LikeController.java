package com.sociablesphere.postsociablesphere.api.controller;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.response.logic.LikeServiceLogic;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/likes")
public class LikeController {

    private final LikeServiceLogic likeServiceLogic;

    public LikeController(LikeServiceLogic likeServiceLogic) {
        this.likeServiceLogic = likeServiceLogic;
    }

    @PostMapping("/like")
    public Mono<ResponseEntity<Void>> performLike(@Valid @RequestBody LikeDto likeDto) {
        return likeServiceLogic.performLikeAndBuildResponse(likeDto);
    }

    @PostMapping("/dislike")
    public Mono<ResponseEntity<Void>> performDislike(@Valid @RequestBody LikeDto likeDto) {
        return likeServiceLogic.performDislikeAndBuildResponse(likeDto);
    }
}
