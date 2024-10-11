package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.model.Likes;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LikeRepository extends R2dbcRepository<Likes, Likes.LikeId> {

    Mono<Likes> findById(Likes.LikeId id);

    Flux<Likes> findByIdPostId(Long postId);

    Mono<Void> deleteByIdPostId(Long postId);
}

