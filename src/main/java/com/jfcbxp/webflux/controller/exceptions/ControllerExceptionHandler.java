package com.jfcbxp.webflux.controller.exceptions;

import com.jfcbxp.webflux.service.exception.ObjectNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Mono<StandardError>> duplicateKeyException(
            DuplicateKeyException exception,
            ServerHttpRequest request) {
        return ResponseEntity.badRequest().body(Mono.just(StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("E-mail already registered")
                .path(request.getPath().toString())
                .build()));

    }

    @ExceptionHandler(ObjectNotFoundException.class)
    ResponseEntity<Mono<StandardError>> objectNotFoundException(
            ObjectNotFoundException exception,
            ServerHttpRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Mono.just(StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .path(request.getPath().toString())
                .build()));

    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<ValidationError>> validationError(
            WebExchangeBindException exception, ServerHttpRequest request
    ) {
        ValidationError error = new ValidationError(
                LocalDateTime.now(),
                request.getPath().toString(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "E-mail already registered"
                );

        for(FieldError err : exception.getBindingResult().getFieldErrors()){
            error.addError(err.getField(),err.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Mono.just(error));

    }
}
