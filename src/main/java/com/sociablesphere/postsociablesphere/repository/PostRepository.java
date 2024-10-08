package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.model.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {
}
