package com.inmaytide.orbit.commons.exception.handler;

import com.inmaytide.orbit.commons.exception.PathNotFoundException;
import com.inmaytide.orbit.commons.exception.handler.parser.DefaultErrorParser;
import com.inmaytide.orbit.commons.exception.handler.parser.ErrorParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ErrorParser parser;

    public GlobalExceptionHandler() {
        this(new DefaultErrorParser());
    }

    public GlobalExceptionHandler(ErrorParser parser) {
        this.parser = parser;
    }

    @Override
    @NonNull
    public Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable e) {
        String path = exchange.getRequest().getPath().pathWithinApplication().value();
        log.error("Handing error: {}, {}, {}", path, e.getClass().getSimpleName(), e.getMessage());
        return Mono.just(parser.parse(e))
                .transform(mono -> ThrowableTranslator.translate(mono, path))
                .flatMap(translator -> translator.write(exchange.getResponse()));
    }

    @NonNull
    public static Mono<ServerResponse> notFound(final ServerRequest request) {
        log.error("Handing error: {}, {}, {}", request.path(), PathNotFoundException.class.getSimpleName(), null);
        return Mono.just(new PathNotFoundException())
                .transform(mono -> ThrowableTranslator.translate(mono, request.path()))
                .flatMap(ThrowableTranslator::getResponse);
    }

}
