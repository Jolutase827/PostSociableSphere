package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.model.Likes;

import java.time.LocalDateTime;

public class LikeMapper {

    public static Likes toEntity(LikeDto dto) {
        return Likes.builder()
                .userId(dto.getUserId())
                .postId(dto.getPostId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static LikeResponseDto toResponseDto(Likes likes) {
        return LikeResponseDto.builder()
                .userId(likes.getUserId())
                .createdAt(likes.getCreatedAt())
                .build();
    }

}
