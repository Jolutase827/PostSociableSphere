package com.sociablesphere.postsociablesphere.service.post;

import com.sociablesphere.postsociablesphere.api.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface PostService {

    Mono<PostResponseDto> createPost(PostCreationDto postCreationDto);
    Mono<PostResponseDto> updatePost(PostUpdateDto postUpdateDto);
    Mono<Void> deletePost(Long idPost);
    Mono<UserResponseDto> addOwner(NewOwnerDto newOwner);
}
