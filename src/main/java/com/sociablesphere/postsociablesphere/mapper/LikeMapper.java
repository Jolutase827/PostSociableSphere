package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.model.Like;

import java.time.LocalDateTime;

public class LikeMapper {

    public static Like toEntity(LikeDto dto) {
        return Like.builder()
                .id( Like.LikeId
                        .builder()
                        .userId(dto.getPostId())
                        .postId(dto.getUserId())
                        .build())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static LikeResponseDto toResponseDto(Like like) {
        return LikeResponseDto.builder()
                .userId(like.getId().getUserId())
                .createdAt(like.getCreatedAt())
                .build();
    }

    public static Like.LikeId createLikeId(LikeDto likeDto) {
        return Like.LikeId.builder()
                .userId(likeDto.getUserId())
                .postId(likeDto.getPostId())
                .build();
    }
}

