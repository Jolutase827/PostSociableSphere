package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.model.Post;
import com.sociablesphere.postsociablesphere.model.PostUser;

public class PostUserMapper {

    public static PostUser toEntity(Post post, UserResponseDto user){
        return PostUser.builder()
                .id(PostUser.PostUserId.builder()
                        .postId(post.getId())
                        .userId(user.getId())
                        .build())
                .build();
    }

}
