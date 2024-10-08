package com.sociablesphere.postsociablesphere.repository;

import com.sociablesphere.postsociablesphere.api.dto.UserDetailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    public void testFindByApiToken_Success() {
        // Arrange
        String apiToken = "validApiToken";
        UserDetailDTO mockUserDetail = new UserDetailDTO();
        mockUserDetail.setId(1L);
        mockUserDetail.setUserName("testUser");
        mockUserDetail.setEmail("test@test.com");

        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString(), eq(apiToken));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.just(mockUserDetail)).when(responseSpec).bodyToMono(UserDetailDTO.class);

        // Act
        Mono<UserDetailDTO> result = userRepository.findByApiToken(apiToken);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(user -> user.getId().equals(1L) && user.getUserName().equals("testUser"))
                .verifyComplete();

        // Verify that the WebClient chain was called as expected
        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(anyString(), eq(apiToken));
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToMono(UserDetailDTO.class);
    }

    @Test
    public void testFindById_Success() {
        // Arrange
        Long userId = 1L;
        UserDetailDTO mockUserDetail = new UserDetailDTO();
        mockUserDetail.setId(userId);
        mockUserDetail.setUserName("testUser");
        mockUserDetail.setEmail("test@test.com");

        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString(), eq(userId));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.just(mockUserDetail)).when(responseSpec).bodyToMono(UserDetailDTO.class);

        // Act
        Mono<UserDetailDTO> result = userRepository.findById(userId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(user -> user.getId().equals(userId) && user.getUserName().equals("testUser"))
                .verifyComplete();

        // Verify that the WebClient chain was called as expected
        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(anyString(), eq(userId));
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToMono(UserDetailDTO.class);
    }


    //TEMPORARY TEST NOT UNIT TEST. TESTS ACTUAL CONNECTION TO MICROSERVICE.
    @Test
    public void testGetUserByApiToken() {
        String apiToken = "bDC17oLBDIQU9Id4UfHY4d1I69O3igqSnBtGIegGaOAo1jVSpsb2k9dBAgVm3zhE398NgUtrgBSzQR3MIC3WAvyfg9dt5N4tzkRekJFmCWhikFHiYbCTr6UT71slKH11LeNXhFgsD02P37yFWimiZ1biiJivu4Xtt6ONtuf7MtxiX7igtS54tu5HT7MCn82FnUlfmlsHOS7Gl2voykZLHOaC67CwfFZ4e4a0AY5dcD4MZSz2NELBsi24ASqz4CC";

        WebClient webClient = WebClient.create("http://localhost:8080");
        Mono<UserDetailDTO> response = webClient.get()
                .uri("/v1/users/apiToken?apiToken=" + apiToken)
                .retrieve()
                .bodyToMono(UserDetailDTO.class);

        StepVerifier.create(response)
                .expectNextMatches(user -> user.getUserName().equals("john_doe"))
                .verifyComplete();
    }

    //TEMPORARY TEST NOT UNIT TEST. TESTS ACTUAL CONNECTION TO MICROSERVICE.
    @Test
    public void testGetUserById() {
        Long userId = 1L; // Replace with a valid user ID in your system

        WebClient webClient = WebClient.create("http://localhost:8080");
        Mono<UserDetailDTO> response = webClient.get()
                .uri("/v1/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserDetailDTO.class);

        StepVerifier.create(response)
                .expectNextMatches(user -> user.getId().equals(userId) && user.getUserName().equals("john_doe")) // Replace "john_doe" with the expected username
                .verifyComplete();
    }



}
