package com.sociablesphere.postsociablesphere.service.postuser.impl;

import com.sociablesphere.postsociablesphere.model.PostUser;
import com.sociablesphere.postsociablesphere.repository.PostUserRepository;
import static com.sociablesphere.postsociablesphere.service.data.DataPostUserServiceImplTest.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostUserServiceImplTest {

    @Mock
    private PostUserRepository postUserRepository;

    @InjectMocks
    private PostUserServiceImpl postUserService;

    @Nested
    @DisplayName("Save PostUser")
    class SavePostUserTests {
        @Test
        @DisplayName("Given a Post and UserResponseDto, when savePostUser is called, then PostUser is saved")
        void savePostUserValid() {
            // Given
            when(postUserRepository.save(any())).thenReturn(Mono.just(POST_USER));

            // When
            Mono<PostUser> result = postUserService.savePostUser(POST.getId(), USER_RESPONSE_DTO.getId());

            // Then
            StepVerifier.create(result)
                    .expectNext(POST_USER)
                    .verifyComplete();

            verify(postUserRepository).save(any());
        }
    }

    @Nested
    @DisplayName("Delete All PostUsers By Post ID")
    class DeleteAllPostUsersByPostIdTests {
        @Test
        @DisplayName("Given a valid postId, when deleteAllPostUserByPostId is called, then PostUsers are deleted")
        void deleteAllPostUsersByPostIdValid() {
            // Given
            when(postUserRepository.deleteByPostId(POST_ID)).thenReturn(Mono.empty());

            // When
            Mono<Void> result = postUserService.deleteAllPostUserByPostId(POST_ID);

            // Then
            StepVerifier.create(result)
                    .verifyComplete();

            verify(postUserRepository).deleteByPostId(POST_ID);
        }
    }

    @Nested
    @DisplayName("Find All PostUsers By Post ID")
    class FindAllPostUsersByPostIdTests {
        @Test
        @DisplayName("Given a valid postId, when findAllPostUserByPostId is called, then return Flux of PostUser")
        void findAllPostUsersByPostIdValid() {
            // Given
            when(postUserRepository.findByPostId(POST_ID)).thenReturn(Flux.just(POST_USER));

            // When
            Flux<PostUser> result = postUserService.findAllPostUserByPostId(POST_ID);

            // Then
            StepVerifier.create(result)
                    .expectNext(POST_USER)
                    .verifyComplete();

            verify(postUserRepository).findByPostId(POST_ID);
        }
    }
}
