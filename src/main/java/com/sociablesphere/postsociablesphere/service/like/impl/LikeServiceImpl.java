package com.sociablesphere.postsociablesphere.service.like.impl;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import com.sociablesphere.postsociablesphere.mapper.LikeMapper;
import com.sociablesphere.postsociablesphere.model.Likes;
import com.sociablesphere.postsociablesphere.repository.LikeRepository;
import com.sociablesphere.postsociablesphere.repository.PostRepository;
import com.sociablesphere.postsociablesphere.service.like.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Override
    public Mono<Long> performLike(LikeDto likeDto) {
        return postRepository.findById(likeDto.getPostId())
                .switchIfEmpty(Mono.error(new ExternalMicroserviceException("The post with the id " + likeDto.getPostId() + " does not exist")))
                .flatMap(post -> createLikeIfNotExists(likeDto));
    }

    @Override
    public Mono<Long> performDislike(LikeDto likeDto) {
        return likeRepository.findById(likeDto.getId())
                .switchIfEmpty(Mono.error(new ExternalMicroserviceException("The user with the id " + likeDto.getUserId() + " didn't like the post already")))
                .flatMap(this::deleteLikeAndReturnPostId);
    }

    @Override
    public Mono<Set<LikeResponseDto>> getLikesByPostId(Long postId) {
        return likeRepository.findByIdPostId(postId)
                .map(LikeMapper::toResponseDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Mono<Void> deleteAllLikesByPostId(Long postId) {
        return likeRepository.deleteByIdPostId(postId);
    }


    private Mono<Long> createLikeIfNotExists(LikeDto likeDto) {
        return likeRepository.existsById(likeDto.getId())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ExternalMicroserviceException("The user with the id " + likeDto.getUserId() + " already liked the post"));
                    }
                    return saveLike(likeDto);
                });
    }



    private Mono<Long> saveLike( LikeDto likeDto) {
        Likes like = LikeMapper.toEntity(likeDto);
        return likeRepository.save(like).thenReturn(likeDto.getPostId());
    }

    private Mono<Long> deleteLikeAndReturnPostId(Likes like) {
        return likeRepository.deleteById(like.getId()).thenReturn(like.getPostId());
    }
}