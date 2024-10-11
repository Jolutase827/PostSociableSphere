package com.sociablesphere.postsociablesphere.service.data;


import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import java.util.Random;

public class DataUserServiceImplTest {
    public static final Long USER_ID = Math.abs(new Random().nextLong());



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
