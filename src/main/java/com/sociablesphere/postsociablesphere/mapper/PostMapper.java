package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.mapper.modelmapper.PostCreationDTOtoPostMap;
import com.sociablesphere.postsociablesphere.mapper.modelmapper.PostUpdateDTOtoPostMap;
import com.sociablesphere.postsociablesphere.model.Post;
import org.modelmapper.ModelMapper;
import reactor.util.function.Tuple2;

import java.util.Set;

public class PostMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        configureMappings();
    }

    private static void configureMappings() {
        modelMapper.addMappings(new PostCreationDTOtoPostMap());
        modelMapper.addMappings(new PostUpdateDTOtoPostMap());
    }

    public static Post toEntity(PostCreationDto postCreationDto) {
        return modelMapper.map(postCreationDto, Post.class);
    }

    public static PostResponseDto toDto(Post post) {
        return modelMapper.map(post, PostResponseDto.class);
    }

    public static PostResponseDto toDto(Post post, Set<LikeResponseDto> likes, Set<UserResponseDto> owners) {
        PostResponseDto dto = modelMapper.map(post, PostResponseDto.class);
        dto.setLikes(likes);
        dto.setPostOwners(owners);
        return dto;
    }


    public static void updateEntityFromDto(Post post, PostUpdateDto dto) {
        modelMapper.map(dto, post);
    }

    public static PostResponseDto zipPostLikesUsersToPostResponse(Post post, Tuple2<Set<LikeResponseDto>, Set<UserResponseDto>> tuple) {
        Set<LikeResponseDto> likes = tuple.getT1();
        Set<UserResponseDto> owners = tuple.getT2();
        return PostMapper.toDto(post, likes, owners);
    }

}

