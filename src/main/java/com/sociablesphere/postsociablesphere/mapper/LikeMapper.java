package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.model.Likes;
import org.modelmapper.ModelMapper;

public class LikeMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Likes toEntity(LikeDto dto) {
        return modelMapper.map(dto, Likes.class);
    }

    public static LikeResponseDto toResponseDto(Likes likes) {
        return modelMapper.map(likes, LikeResponseDto.class);
    }
}
