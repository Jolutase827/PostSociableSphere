package com.sociablesphere.postsociablesphere.service.post.impl;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import com.sociablesphere.postsociablesphere.mapper.PostMapper;
import com.sociablesphere.postsociablesphere.model.Post;
import com.sociablesphere.postsociablesphere.repository.PostRepository;
import com.sociablesphere.postsociablesphere.service.like.LikeService;
import com.sociablesphere.postsociablesphere.service.post.PostService;

import com.sociablesphere.postsociablesphere.service.postuser.PostUserService;
import com.sociablesphere.postsociablesphere.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

import static com.sociablesphere.postsociablesphere.mapper.PostMapper.zipPostLikesUsersToPostResponse;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private PostUserService postUserService;
    private final UserService userService;
    private LikeService likeService;


    @Override
    public Mono<PostResponseDto> createPost(PostCreationDto postCreationDto) {
        return userService.getUserOrThrow(postCreationDto.getUserId())
                .flatMap(user -> createNewPost(postCreationDto, user));
    }

    @Override
    public Mono<PostResponseDto> updatePost(Long id, PostUpdateDto postUpdateDto) {
        return getPostOrThrow(id)
                .flatMap(existingPost -> updateExistingPost(existingPost, postUpdateDto))
                .flatMap(this::buildPostResponseDto);
    }

    @Override
    public Mono<Void> deletePost(Long idPost) {
        return ensurePostExists(idPost)
                .flatMap(exists -> deletePostData(idPost));
    }


    @Override
    public Mono<Post> getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(new ExternalMicroserviceException("The post with the id " + postId + " not exist")));
    }

    @Override
    public Mono<UserResponseDto> addOwner(NewOwnerDto newOwnerDto) {
        return userService.getUserOrThrow(newOwnerDto.getUserId())
                .flatMap(userResponseDto ->
                        postUserService.savePostUser(newOwnerDto.getPostId(), newOwnerDto.getUserId())
                                .then(Mono.just(userResponseDto))
                );
    }

    private Mono<PostResponseDto> createNewPost(PostCreationDto postCreationDto, UserResponseDto user) {
        Post post = PostMapper.toEntity(postCreationDto);
        return postRepository.save(post)
                .flatMap(savedPost -> postUserService.savePostUser(savedPost.getId(),user.getId()).thenReturn(savedPost))
                .flatMap(this::buildPostResponseDto);
    }

    private Mono<Post> updateExistingPost(Post existingPost, PostUpdateDto postUpdateDto) {
        PostMapper.updateEntityFromDto(existingPost, postUpdateDto);
        return postRepository.save(existingPost);
    }

    private Mono<Boolean> ensurePostExists(Long postId) {
        return postRepository.existsById(postId)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new ExternalMicroserviceException("The post with the id " + postId + " not exist"));
                    }
                    return Mono.just(true);
                });
    }

    private Mono<Void> deletePostData(Long postId) {
        Mono<Void> deletePostUsers = postUserService.deleteAllPostUserByPostId(postId);
        Mono<Void> deleteLikes = likeService.deleteAllLikesByPostId(postId);
        Mono<Void> deletePost = postRepository.deleteById(postId);
        return Mono.when(deletePostUsers, deleteLikes, deletePost);
    }



    private Mono<PostResponseDto> buildPostResponseDto(Post post) {
        Mono<Set<LikeResponseDto>> likesMono = likeService.getLikesByPostId(post.getId());
        Mono<Set<UserResponseDto>> ownersMono = getOwnersOfPosts(post);
        return Mono.zip(likesMono, ownersMono)
                .map(tuple -> zipPostLikesUsersToPostResponse(post, tuple));
    }



    private Mono<Set<UserResponseDto>> getOwnersOfPosts(Post post) {
        return postUserService.findAllPostUserByPostId(post.getId())
                .flatMap(postUser -> userService.getUserOrThrow(postUser.getUserId()))
                .collect(Collectors.toSet());
    }



}