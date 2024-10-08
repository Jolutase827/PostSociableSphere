package com.sociablesphere.postsociablesphere.service.post.impl;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.service.post.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostServiceImpl implements PostService {

    @Override
    public Mono<PostResponseDto> createPost(PostCreationDto postCreationDto) {
        return null;
    }

    @Override
    public Mono<PostResponseDto> updatePost(PostUpdateDto postUpdateDto) {
        return null;
    }

    @Override
    public Mono<Void> deletePost(Long idPost) {
        return null;
    }

    @Override
    public Mono<UserResponseDto> addOwner(NewOwnerDto newOwner) {
        return null;
    }
}
