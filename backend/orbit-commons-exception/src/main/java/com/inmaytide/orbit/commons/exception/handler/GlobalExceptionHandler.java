package com.inmaytide.orbit.commons.exception.handler;

import com.inmaytide.orbit.commons.exception.PathNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    @NonNull
    public Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable e) {
        e = cast(e);
        String path = exchange.getRequest().getPath().pathWithinApplication().value();
        log.error("Exception handling......", e);
        return Mono.just(e)
                .transform(mono -> ThrowableTranslator.translate(mono, path))
                .flatMap(translator -> translator.write(exchange.getResponse()));
    }

    @NonNull
    public static Mono<ServerResponse> notFound(final ServerRequest request) {
        log.error("Path [{}] not found.", request.path());
        return Mono.just(new PathNotFoundException(request.path()))
                .transform(ThrowableTranslator::translate)
                .flatMap(ThrowableTranslator::getResponse);
    }

    private Throwable cast(Throwable e) {
        if (e instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) e;
            if (ex.getStatus() == HttpStatus.NOT_FOUND) {
                e = new PathNotFoundException(e);
            }
        }
        return e;
    }

}
