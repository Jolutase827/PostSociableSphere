package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.model.PostUser;
import org.modelmapper.ModelMapper;

public class PostUserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static PostUser toEntity(Long postId, Long userId) {
        return PostUser.builder()
                .postId(postId)
                .userId(userId)
                .build();
    }

}
