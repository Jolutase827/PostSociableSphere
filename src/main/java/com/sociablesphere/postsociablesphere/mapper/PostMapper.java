package com.sociablesphere.postsociablesphere.mapper;

import com.sociablesphere.postsociablesphere.api.dto.PostCreationDto;
import com.sociablesphere.postsociablesphere.api.dto.PostResponseDto;
import com.sociablesphere.postsociablesphere.api.dto.PostUpdateDto;
import com.sociablesphere.postsociablesphere.model.Post;

import java.time.LocalDateTime;

public class PostMapper {

    public static Post toEntity(PostCreationDto dto) {
        return Post.builder()
                .content(dto.getContent())
                .type(dto.getType())
                .isPaid(dto.getIsPaid())
                .cost(dto.getCost())
                .isAd(dto.getIsAd())
                .maxViews(dto.getMaxViews())
                .viewsRemaining(dto.getMaxViews()) // Inicialmente igual a maxViews
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
}

