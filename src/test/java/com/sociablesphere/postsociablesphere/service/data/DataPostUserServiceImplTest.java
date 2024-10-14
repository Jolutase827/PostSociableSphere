package com.sociablesphere.postsociablesphere.service.data;

import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.model.Post;
import com.sociablesphere.postsociablesphere.model.PostUser;

import java.util.Random;

public class DataPostUserServiceImplTest {
    public static final Long POST_ID = Math.abs(new Random().nextLong());
    public static final Long USER_ID = Math.abs(new Random().nextLong());

    public static final Post POST = Post.builder()
            .id(POST_ID)
            .content("Test Content")
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

    public static final PostUser POST_USER = PostUser.builder()
                    .id(2L)
                    .postId(POST_ID)
                    .userId(USER_ID)
                    .build();
}

