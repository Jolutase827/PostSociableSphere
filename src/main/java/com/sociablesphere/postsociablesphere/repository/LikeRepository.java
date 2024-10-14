package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.model.Likes;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LikeRepository extends R2dbcRepository<Likes, Long> {


    Flux<Likes> findByPostId(Long postId);

    Mono<Void> deleteByPostId(Long postId);
}

