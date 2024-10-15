package com.sociablesphere.postsociablesphere.mapper.modelmapper;

import com.sociablesphere.postsociablesphere.api.dto.PostCreationDto;
import com.sociablesphere.postsociablesphere.model.Post;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;

public class PostCreationDTOtoPostMap extends PropertyMap<PostCreationDto, Post> {
    @Override
    protected void configure() {
        skip(destination.getId());
        using(ctx -> LocalDateTime.now()).map(source, destination.getCreatedAt());
        using(ctx -> LocalDateTime.now()).map(source, destination.getUpdatedAt());
        using(ctx -> 0).map(source, destination.getViewsRemaining());
    }
}