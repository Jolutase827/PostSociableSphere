package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.api.dto.UserDetailDTO;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<UserDetailDTO> findByApiToken(String apiToken);
}
