package com.sociablesphere.postsociablesphere.service.user.impl;

import com.sociablesphere.postsociablesphere.api.dto.UserResponseDto;
import com.sociablesphere.postsociablesphere.exceptions.InvalidCredentialsException;
import com.sociablesphere.postsociablesphere.repository.UserRepository;
import com.sociablesphere.postsociablesphere.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserResponseDto> getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new InvalidCredentialsException("The user with the id " + userId + " does not exist")));
    }
}
