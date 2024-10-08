package com.sociablesphere.postsociablesphere.service.like.impl;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.service.like.LikeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class LikeServiceImpl implements LikeService {
    @Override
    public Mono<Long> perfomLike(LikeDto likeDto) {
        return null;
    }

    @Override
    public Mono<Long> performDislike(LikeDto likeDto) {
        return null;
    }
}
