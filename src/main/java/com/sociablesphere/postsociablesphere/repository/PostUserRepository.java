package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.model.PostUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PostUserRepository extends ReactiveCrudRepository<PostUser, PostUser.PostUserId> {

    Flux<PostUser> findByIdPostId(Long postId);

    Mono<Void> deleteByIdPostId(Long postId);
}

