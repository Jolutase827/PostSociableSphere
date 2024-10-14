package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.model.Post;
import com.sociablesphere.postsociablesphere.model.PostUser;

public class PostUserMapper {

    public static PostUser toEntity(Long postId, Long userId){
        return PostUser.builder()
                .postId(postId)
                .userId(userId)
                .build();
    }

}
