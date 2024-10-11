package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.model.Post;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends R2dbcRepository<Post, Long> {
}
