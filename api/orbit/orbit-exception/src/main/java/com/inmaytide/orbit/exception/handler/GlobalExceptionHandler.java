package com.inmaytide.orbit.exception.handler;

import com.inmaytide.orbit.exception.PathNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

public class GlobalExceptionHandler implements WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    @Nonnull
    public Mono<Void> handle(@Nonnull ServerWebExchange exchange, @Nonnull Throwable e) {
        return Mono.just(e)
                .transform(ThrowableTranslator::translate)
                .flatMap(translator -> translator.write(exchange.getResponse()));
    }

    @Nonnull
    public Mono<ServerResponse> notFound(final ServerRequest request) {
        log.error("Path [{}] not found.", request.path());
        return Mono.just(new PathNotFoundException(request.path())).transform(this::getResponse);
    }

    private <T extends Throwable> Mono<ServerResponse> getResponse(final Mono<T> monoError) {
        return monoError.transform(ThrowableTranslator::translate)
                .flatMap(ThrowableTranslator::getResponse);
    }
}
