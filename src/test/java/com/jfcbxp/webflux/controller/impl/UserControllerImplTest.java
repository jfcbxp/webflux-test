package com.jfcbxp.webflux.controller.impl;

import com.jfcbxp.webflux.entity.User;
import com.jfcbxp.webflux.mapper.UserMapper;
import com.jfcbxp.webflux.model.request.UserRequest;
import com.jfcbxp.webflux.model.response.UserResponse;
import com.jfcbxp.webflux.service.UserService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private UserService service;
    @MockBean
    private UserMapper mapper;
    @MockBean
    private MongoClient mongoClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test endpoint save with success")
    void testSaveWithSuccess() {
        UserRequest request = new UserRequest("test","test@test.com","test");

        Mockito.when(service.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

        webTestClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(service,Mockito.times(1)).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint save bad request")
    void testSaveWithBadRequest() {
        UserRequest request = new UserRequest("test","test@test.com","test");

        Mockito.when(service.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

        webTestClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.error[0].fieldName").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes");



        Mockito.verify(service,Mockito.times(1)).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint findById with Success")
    void testFindByIdWithSuccess() {
        UserRequest request = new UserRequest("test","test@test.com","test");

        Mockito.when(service.findById(anyString())).thenReturn(Mono.just(User.builder().build()));

        webTestClient.get().uri("/users/12345").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.error[0].fieldName").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes");



        Mockito.verify(service,Mockito.times(1)).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint update with success")
    void testUpdateWithSuccess() {
        UserRequest request = new UserRequest("test","test@test.com","test");
        UserResponse response = new UserResponse("test","test","test@test.com","test");

        Mockito.when(service.update(anyString(),any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));
        Mockito.when(mapper.toResponse(any(User.class))).thenReturn(response);

        webTestClient.patch().uri("/users/12345").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.error[0].fieldName").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes");



        Mockito.verify(service,Mockito.times(1)).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint delete")
    void testDeleteWithSuccess() {

        Mockito.when(service.delete(anyString())).thenReturn(Mono.just(User.builder().build()));

        webTestClient.delete().uri("/users/12345")
                .exchange()
                .expectStatus().isOk();



        Mockito.verify(service,Mockito.times(1)).save(any(UserRequest.class));
    }
}