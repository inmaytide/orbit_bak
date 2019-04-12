package com.inmaytide.orbit.commons.exception.handler;

import com.inmaytide.orbit.commons.exception.PathNotFoundException;
import com.inmaytide.orbit.commons.exception.handler.parser.DefaultThrowableParser;
import com.inmaytide.orbit.commons.exception.handler.parser.ThrowableParser;
import com.inmaytide.orbit.commons.exception.handler.parser.ThrowableTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

public class DefaultExceptionHandler implements WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    private final ThrowableParser parser;

    public DefaultExceptionHandler() {
        this(new DefaultThrowableParser());
    }

    public DefaultExceptionHandler(ThrowableParser parser) {
        this.parser = parser;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        var path = exchange.getRequest().getPath().pathWithinApplication().value();
        log.error("Handing error: {}, {}, {}", throwable.getClass().getName(), throwable.getMessage(), path);
        return Mono.just(parser.parse(throwable))
                .transform(mono -> ThrowableTranslator.translate(mono, path))
                .flatMap(translator -> translator.write(exchange.getResponse()));
    }


    public Mono<ServerResponse> notFound(final ServerRequest request) {
        log.error("Handing error: {}, {}, {}", PathNotFoundException.class.getSimpleName(), null, request.path());
        return Mono.just(new PathNotFoundException())
                .transform(mono -> ThrowableTranslator.translate(mono, request.path()))
                .flatMap(ThrowableTranslator::getResponse);
    }

}
