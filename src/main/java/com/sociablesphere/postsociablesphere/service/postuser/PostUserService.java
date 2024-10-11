package com.sociablesphere.postsociablesphere.service.postuser;

import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.model.Post;
import com.sociablesphere.postsociablesphere.model.PostUser;
import com.sociablesphere.postsociablesphere.service.user.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface PostUserService {
    Mono<PostUser> savePostUser(Long postId, Long userId);
    Mono<Void> deleteAllPostUserByPostId(Long postId);
    Flux<PostUser> findAllPostUserByPostId(Long postId);
}
