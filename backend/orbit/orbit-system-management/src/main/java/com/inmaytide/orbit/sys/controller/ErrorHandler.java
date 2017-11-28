package com.inmaytide.orbit.sys.controller;

import com.inmaytide.orbit.exception.PathNotFoundException;
import com.inmaytide.orbit.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author Moss
 * @since November 28, 2017
 */
public class ErrorHandler implements ErrorWebExceptionHandler {

    private WebExceptionHandler exceptionHandler;

    public ErrorHandler() {
        this.exceptionHandler = new GlobalExceptionHandler();
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof ResponseStatusException) {
            ResponseStatusException e = (ResponseStatusException) ex;
            if (e.getStatus() == HttpStatus.NOT_FOUND) {
                ex = new PathNotFoundException();
            }
        }
        return this.exceptionHandler.handle(exchange, ex);
    }
}
