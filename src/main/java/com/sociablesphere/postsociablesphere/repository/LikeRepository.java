package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.model.Like;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LikeRepository extends ReactiveCrudRepository<Like, Like.LikeId> {

    Mono<Like> findById(Like.LikeId id);

    Flux<Like> findByIdPostId(Long postId);

    Mono<Void> deleteByIdPostId(Long postId);
}

