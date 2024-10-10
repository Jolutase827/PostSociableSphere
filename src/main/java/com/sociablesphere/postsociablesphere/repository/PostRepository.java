package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.model.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ReactiveCrudRepository<Post, Long> {
}
