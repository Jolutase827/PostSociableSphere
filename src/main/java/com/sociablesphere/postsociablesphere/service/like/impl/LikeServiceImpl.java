package com.sociablesphere.postsociablesphere.service.like.impl;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.exceptions.ExternalMicroserviceException;
import com.sociablesphere.postsociablesphere.model.Like;
import com.sociablesphere.postsociablesphere.repository.LikeRepository;
import com.sociablesphere.postsociablesphere.repository.PostRepository;
import com.sociablesphere.postsociablesphere.service.like.LikeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private  LikeRepository likeRepository;
    private  PostRepository postRepository;



    @Override
    public Mono<Long> performLike(LikeDto likeDto) {
        return postRepository.findById(likeDto.getPostId())
                .switchIfEmpty(Mono.error(new ExternalMicroserviceException("Post no encontrado")))
                .flatMap(post -> {
                    Like.LikeId likeId = Like.LikeId.builder()
                            .userId(likeDto.getUserId())
                            .postId(likeDto.getPostId())
                            .build();
                    return likeRepository.existsById(likeId)
                            .flatMap(exists -> {
                                if (exists)
                                    return Mono.error(new ExternalMicroserviceException("El usuario ya ha dado like a este post"));
                                Like like = Like.builder()
                                            .id(likeId)
                                            .createdAt(LocalDateTime.now())
                                            .build();
                                return likeRepository.save(like)
                                            .thenReturn(post.getId());

                            });
                });
    }




    @Override
    public Mono<Long> performDislike(LikeDto likeDto) {
        Like.LikeId likeId = new Like.LikeId(likeDto.getPostId(), likeDto.getUserId());
        return likeRepository.findById(likeId)
                .switchIfEmpty(Mono.error(new ExternalMicroserviceException("Like no encontrado")))
                .flatMap(like -> likeRepository.delete(like).thenReturn(like.getId().getPostId()));
    }
}