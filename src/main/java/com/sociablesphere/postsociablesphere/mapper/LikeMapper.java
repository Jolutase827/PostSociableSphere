package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.model.Likes;

import java.time.LocalDateTime;

public class LikeMapper {

    public static Likes toEntity(LikeDto dto) {
        return Likes.builder()
                .id( Likes.LikeId
                        .builder()
                        .userId(dto.getPostId())
                        .postId(dto.getUserId())
                        .build())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static LikeResponseDto toResponseDto(Likes likes) {
        return LikeResponseDto.builder()
                .userId(likes.getId().getUserId())
                .createdAt(likes.getCreatedAt())
                .build();
    }

    public static Like.LikeId createLikeId(LikeDto likeDto) {
        return Like.LikeId.builder()
                .userId(likeDto.getUserId())
                .postId(likeDto.getPostId())
                .build();
    }
}

