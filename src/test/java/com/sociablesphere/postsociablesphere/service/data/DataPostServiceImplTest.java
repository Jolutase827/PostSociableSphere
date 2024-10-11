package com.sociablesphere.postsociablesphere.service.data;

import com.sociablesphere.postsociablesphere.api.dto.*;
import com.sociablesphere.postsociablesphere.model.Post;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

public class DataPostServiceImplTest {
    public static final Long POST_ID = Math.abs(new Random().nextLong());
    public static final Long USER_ID = Math.abs(new Random().nextLong());

    public static final PostCreationDto POST_CREATION_DTO = PostCreationDto.builder()
            .content("Test Content")
            .type("text")
            .isPaid(false)
            .cost(BigDecimal.ZERO)
            .isAd(false)
            .maxViews(100)
            .footer("Test Footer")
            .parentId(null)
            .userId(USER_ID)
            .build();

    public static final PostUpdateDto POST_UPDATE_DTO = PostUpdateDto.builder()
            .content("Updated Content")
            .isPaid(true)
            .cost(BigDecimal.valueOf(10))
            .isAd(true)
            .maxViews(200)
            .footer("Updated Footer")
            .build();

    public static final Post POST = Post.builder()
            .id(POST_ID)
            .content("Test Content")
            .type("text")
            .isPaid(false)
            .cost(BigDecimal.ZERO)
            .isAd(false)
            .maxViews(100)
            .viewsRemaining(100)
            .footer("Test Footer")
            .parentId(null)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    public static final UserResponseDto USER_RESPONSE_DTO = UserResponseDto.builder()
            .id(USER_ID)
            .userName("TestUser")
            .name("Test")
            .lastName("User")
            .email("testuser@example.com")
            .photo("/images/testuser.png")
            .description("Test User Description")
            .role("normal")
            .build();
}