package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.model.Like;

public class LikeMapper {

    public LikeDto toEntity(Like like){
        return LikeDto.builder()
                .postId(like.getId().getPostId())
                .userId(like.getId().getUserId())
                .
                .build();
    }
    public Like toDto(LikeDto like){
        return Like.builder()
                .id(Like.LikeId.builder()
                        .postId(like.getPostId())
                        .userId(like.getUserId())
                        .build())
                .build();
    }
}
