package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.PostCreationDto;
import com.sociablesphere.postsociablesphere.model.Post;

public class PostMapper {

    public Post toEntity(PostCreationDto postCreationDto){
        return Post.builder()

                .build()
    }

}
