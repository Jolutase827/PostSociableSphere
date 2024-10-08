package com.sociablesphere.postsociablesphere.service.post.impl;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import com.sociablesphere.postsociablesphere.exceptions.InvalidCredentialsException;
import com.sociablesphere.postsociablesphere.mapper.LikeMapper;
import com.sociablesphere.postsociablesphere.mapper.PostMapper;
import com.sociablesphere.postsociablesphere.model.Post;
import com.sociablesphere.postsociablesphere.model.PostUser;
import com.sociablesphere.postsociablesphere.repository.LikeRepository;
import com.sociablesphere.postsociablesphere.repository.PostRepository;
import com.sociablesphere.postsociablesphere.repository.PostUserRepository;
import com.sociablesphere.postsociablesphere.repository.UserRepository;
import com.sociablesphere.postsociablesphere.service.post.PostService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private  PostRepository postRepository;
    private PostUserRepository postUserRepository;
    private  UserRepository userRepository;
    private LikeRepository likeRepository;



    @Override
    public Mono<PostResponseDto> createPost(PostCreationDto postCreationDto) {
        return userRepository.findById(postCreationDto.getUserId())
                .switchIfEmpty(Mono.error(new InvalidCredentialsException("Usuario no encontrado")))
                .flatMap(userResponseDto -> {
                    Post post = PostMapper.toEntity(postCreationDto);
                    post.setCreatedAt(LocalDateTime.now());
                    post.setUpdatedAt(LocalDateTime.now());
                    return postRepository.save(post)
                            .flatMap(savedPost -> {
                                PostUser postUser = new PostUser(
                                        new PostUser.PostUserId(savedPost.getId(), userResponseDto.getId())
                                );
                                return postUserRepository.save(postUser)
                                        .thenReturn(savedPost);
                            });
                })
                .flatMap(this::buildPostResponseDto)
                .onErrorMap(e -> new ExternalMicroserviceException("Error al crear el post: " + e.getMessage()));
    }


    @Override
    public Mono<PostResponseDto> updatePost(Long id, PostUpdateDto postUpdateDto) {
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(new ExternalMicroserviceException("Post no encontrado")))
                .flatMap(existingPost -> {
                    PostMapper.updateEntityFromDto(existingPost, postUpdateDto);
                    existingPost.setUpdatedAt(LocalDateTime.now());
                    return postRepository.save(existingPost);
                })
                .flatMap(this::buildPostResponseDto);
    }

    @Override
    public Mono<Void> deletePost(Long idPost) {
        return postRepository.existsById(idPost)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new ExternalMicroserviceException("Post no encontrado"));
                    } else {
                        Mono<Void> deletePostUsers = postUserRepository.deleteByIdPostId(idPost);
                        Mono<Void> deleteLikes = likeRepository.deleteByIdPostId(idPost);
                        Mono<Void> deletePost = postRepository.deleteById(idPost);
                        return Mono.when(deletePostUsers, deleteLikes, deletePost);
                    }
                });
    }

    @Override
    public Mono<UserResponseDto> addOwner(NewOwnerDto newOwner) {
        return postRepository.findById(newOwner.getPostId())
                .switchIfEmpty(Mono.error(new ExternalMicroserviceException("Post no encontrado")))
                .flatMap(post -> userRepository.findById(newOwner.getUserId())
                        .switchIfEmpty(Mono.error(new InvalidCredentialsException("Usuario no encontrado")))
                        .flatMap(userResponseDto -> {
                            PostUser postUser = new PostUser(
                                    new PostUser.PostUserId(post.getId(), userResponseDto.getId())
                            );
                            return postUserRepository.save(postUser)
                                    .thenReturn(userResponseDto);
                        })
                )
                .onErrorMap(e -> new ExternalMicroserviceException("Error al agregar propietario: " + e.getMessage()));
    }



    private Mono<PostResponseDto> buildPostResponseDto(Post post) {
        Mono<Set<LikeResponseDto>> likesMono = likeRepository.findByIdPostId(post.getId())
                .map(LikeMapper::toResponseDto)
                .collect(Collectors.toSet());

        Mono<Set<UserResponseDto>> ownersMono = postUserRepository.findByIdPostId(post.getId())
                .flatMap(postUser -> userRepository.findById(postUser.getId().getUserId()))
                .collect(Collectors.toSet());

        return Mono.zip(likesMono, ownersMono)
                .map(tuple -> {
                    Set<LikeResponseDto> likes = tuple.getT1();
                    Set<UserResponseDto> owners = tuple.getT2();

                    PostResponseDto responseDto = PostMapper.toDto(post);
                    responseDto.setLikes(likes);
                    responseDto.setPostOwners(owners);
                    return responseDto;
                });
    }

}

