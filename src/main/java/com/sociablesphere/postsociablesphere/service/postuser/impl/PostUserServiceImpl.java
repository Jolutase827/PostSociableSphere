package com.sociablesphere.postsociablesphere.service.postuser.impl;

import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.mapper.PostUserMapper;
import com.sociablesphere.postsociablesphere.model.Post;
import com.sociablesphere.postsociablesphere.model.PostUser;
import com.sociablesphere.postsociablesphere.repository.PostUserRepository;
import com.sociablesphere.postsociablesphere.service.post.PostService;
import com.sociablesphere.postsociablesphere.service.postuser.PostUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class PostUserServiceImpl implements PostUserService {

    private PostUserRepository postUserRepository;


    @Override
    public Mono<Void> deleteAllPostUserByPostId(Long postId) {
        return postUserRepository.deleteByIdPostId(postId);
    }

    @Override
    public Mono<PostUser> savePostUser(Post post, UserResponseDto user) {
        PostUser postUser = PostUserMapper.toEntity(post,user);
        return postUserRepository.save(postUser);
    }

    public Flux<PostUser> findAllPostUserByPostId(Long postId){
        return postUserRepository.findByIdPostId(postId);
    }
}
