package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.model.Post;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;
import java.util.Set;

public class PostMapper {

    public static Post toEntity(PostCreationDto dto) {
        return Post.builder()
                .content(dto.getContent())
                .type(dto.getType())
                .isPaid(dto.getIsPaid())
                .cost(dto.getCost())
                .isAd(dto.getIsAd())
                .maxViews(dto.getMaxViews())
                .viewsRemaining(0)
                .footer(dto.getFooter())
                .parentId(dto.getParentId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    public static PostResponseDto toDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .type(post.getType())
                .isPaid(post.getIsPaid())
                .cost(post.getCost())
                .isAd(post.getIsAd())
                .maxViews(post.getMaxViews())
                .viewsRemaining(post.getViewsRemaining())
                .footer(post.getFooter())
                .parentId(post.getParentId())
                .build();
    }

    public static PostResponseDto toDto(Post post, Set<LikeResponseDto> likes, Set<UserResponseDto> owners) {
        return PostResponseDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .type(post.getType())
                .isPaid(post.getIsPaid())
                .cost(post.getCost())
                .isAd(post.getIsAd())
                .maxViews(post.getMaxViews())
                .viewsRemaining(post.getViewsRemaining())
                .footer(post.getFooter())
                .parentId(post.getParentId())
                .likes(likes)
                .postOwners(owners)
                .build();
    }

    public static void updateEntityFromDto(Post post, PostUpdateDto dto) {
        if (dto.getContent() != null) post.setContent(dto.getContent());
        if (dto.getIsPaid() != null) post.setIsPaid(dto.getIsPaid());
        if (dto.getCost() != null) post.setCost(dto.getCost());
        if (dto.getIsAd() != null) post.setIsAd(dto.getIsAd());
        if (dto.getMaxViews() != null) {
            post.setMaxViews(dto.getMaxViews());
        }
        if (dto.getFooter() != null) post.setFooter(dto.getFooter());
        post.setUpdatedAt(LocalDateTime.now());
    }


    public static PostResponseDto zipPostLikesUsersToPostResponse(Post post, Tuple2<Set<LikeResponseDto>, Set<UserResponseDto>> tuple) {
        Set<LikeResponseDto> likes = tuple.getT1();
        Set<UserResponseDto> owners = tuple.getT2();
        return PostMapper.toDto(post, likes, owners);
    }

}

