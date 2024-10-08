package com.sociablesphere.postsociablesphere.service.like;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface LikeService {
    Mono<Long> perfomLike(LikeDto likeDto);
    Mono<Long> performDislike(LikeDto likeDto);
}
