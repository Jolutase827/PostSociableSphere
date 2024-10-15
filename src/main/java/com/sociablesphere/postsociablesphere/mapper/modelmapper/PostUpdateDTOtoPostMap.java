package com.sociablesphere.postsociablesphere.mapper.modelmapper;

import com.sociablesphere.postsociablesphere.api.dto.PostUpdateDto;
import com.sociablesphere.postsociablesphere.model.Post;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;

public class PostUpdateDTOtoPostMap extends PropertyMap<PostUpdateDto, Post> {
    @Override
    protected void configure() {
        using(ctx -> LocalDateTime.now()).map(source, destination.getUpdatedAt());
    }
}
