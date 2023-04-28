package com.jfcbxp.webflux.service;

import com.jfcbxp.webflux.entity.User;
import com.jfcbxp.webflux.mapper.UserMapper;
import com.jfcbxp.webflux.model.request.UserRequest;
import com.jfcbxp.webflux.repository.UserRepository;
import com.jfcbxp.webflux.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    public Mono<User> save(final UserRequest request){
        return repository.save(mapper.toEntity(request));

    }

    public Mono<User> update(final String id, final UserRequest request){
        return findById(id)
                .map(entity -> mapper.toEntity(request,entity))
                .flatMap(repository::save);

    }

    public Mono<User> delete(final String id){
        return handleNotFound(repository.findAndRemove(id));
    }

    public Mono<User> findById(final String id) {
        return handleNotFound(repository.findById(id));
    }

    public Flux<User> findAll() {
        return  repository.findAll();
    }

    private <T> Mono<T> handleNotFound(Mono<T> mono){
        return mono.switchIfEmpty(Mono.error(new ObjectNotFoundException(
                String.format("Object not found, Type: %s",User.class.getSimpleName())
        )));
    }
}
