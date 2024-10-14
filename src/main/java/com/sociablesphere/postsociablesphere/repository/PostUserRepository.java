package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.model.PostUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PostUserRepository extends R2dbcRepository<PostUser, Long> {

    Flux<PostUser> findByPostId(Long postId);

    Mono<Void> deleteByPostId(Long postId);
}

