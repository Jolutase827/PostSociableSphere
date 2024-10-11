package com.sociablesphere.postsociablesphere.service.user;

import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponseDto> getUserOrThrow(Long userId);
}
