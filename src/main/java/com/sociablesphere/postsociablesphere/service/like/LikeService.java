package com.sociablesphere.postsociablesphere.service.like;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
public interface LikeService {
    Mono<Long> performLike(LikeDto likeDto);
    Mono<Long> performDislike(LikeDto likeDto);
    Mono<Set<LikeResponseDto>> getLikesByPostId(Long postId);
    Mono<Void> deleteAllLikesByPostId(Long postId);
}