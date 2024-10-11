package com.sociablesphere.postsociablesphere.service.post;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.model.Post;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface PostService {

    Mono<PostResponseDto> createPost(PostCreationDto postCreationDto);

    Mono<PostResponseDto> updatePost(Long id, PostUpdateDto postUpdateDto);

    Mono<Void> deletePost(Long idPost);

    Mono<Post> getPostOrThrow(Long postId);

    Mono<UserResponseDto> addOwner(NewOwnerDto newOwnerDto);
}
