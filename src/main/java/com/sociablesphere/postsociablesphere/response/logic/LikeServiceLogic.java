package com.sociablesphere.postsociablesphere.response.logic;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.response.service.LikeResponseService;
import com.sociablesphere.postsociablesphere.service.like.LikeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class LikeServiceLogic {

    private static final Logger log = LoggerFactory.getLogger(LikeServiceLogic.class);
    private final LikeService likeService;
    private final LikeResponseService likeResponseService;

    public Mono<ResponseEntity<Void>> performLikeAndBuildResponse(@Valid LikeDto likeDto) {
        log.info("User with ID {} is performing a LIKE on post {}", likeDto.getUserId(), likeDto.getPostId());

        return likeService.performLike(likeDto)
                .flatMap(likesCount -> {
                    log.info("User {} successfully liked post {}", likeDto.getUserId(), likeDto.getPostId());
                    return likeResponseService.buildLikeResponse(likeDto.getPostId(), likeDto.getUserId());
                });
    }

    public Mono<ResponseEntity<Void>> performDislikeAndBuildResponse(@Valid LikeDto likeDto) {
        log.info("User with ID {} is performing a DISLIKE on post {}", likeDto.getUserId(), likeDto.getPostId());

        return likeService.performDislike(likeDto)
                .flatMap(dislikesCount -> {
                    log.info("User {} successfully disliked post {}", likeDto.getUserId(), likeDto.getPostId());
                    return likeResponseService.buildDislikeResponse(likeDto.getPostId(), likeDto.getUserId());
                });
    }
}
