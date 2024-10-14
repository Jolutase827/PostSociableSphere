package com.sociablesphere.postsociablesphere.service.data;


import com.sociablesphere.postsociablesphere.api.dto.LikeDto;
import com.sociablesphere.postsociablesphere.api.dto.LikeResponseDto;
import com.sociablesphere.postsociablesphere.model.Likes;
import com.sociablesphere.postsociablesphere.model.Post;

import java.time.LocalDateTime;
import java.util.Random;

public class DataLikeServiceImplTest {
    public static final Long USER_ID = Math.abs(new Random().nextLong());
    public static final Long POST_ID = Math.abs(new Random().nextLong());

    public static final LikeDto LIKE_DTO = LikeDto.builder()
            .id(2L)
            .userId(USER_ID)
            .postId(POST_ID)
            .build();


    public static final Likes LIKE = Likes.builder()
            .id(2L)
            .userId(USER_ID)
            .postId(POST_ID)
            .createdAt(LocalDateTime.now())
            .build();

    public static final Post POST = Post.builder()
            .id(POST_ID)
            .content("Test content")
            .build();

    public static final LikeResponseDto LIKE_RESPONSE_DTO = LikeResponseDto.builder()
            .userId(USER_ID)
            .createdAt(LIKE.getCreatedAt())
            .build();
}

