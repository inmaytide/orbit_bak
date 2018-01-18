package com.inmaytide.orbit.exception.handler;

import com.inmaytide.orbit.exception.PathNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

public class GlobalExceptionHandler implements WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private boolean showStackTrace = false;

    public GlobalExceptionHandler() {

    }

    public GlobalExceptionHandler(boolean showStackTrace) {
        this.showStackTrace = showStackTrace;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable e) {
        String path = exchange.getRequest().getPath().pathWithinApplication().value();
        log.error("Exception handling [{}] => [{}]", e.getClass().getSimpleName(), e.getMessage());
        if (isShowStackTrace()) {
            e.printStackTrace();
        }
        return Mono.just(e)
                .transform(mono -> ThrowableTranslator.translate(mono, path))
                .flatMap(translator -> translator.write(exchange.getResponse()));
    }

    public Mono<ServerResponse> notFound(final ServerRequest request) {
        log.error("Path [{}] not found.", request.path());
        return Mono.just(new PathNotFoundException(request.path()))
                .transform(ThrowableTranslator::translate)
                .flatMap(ThrowableTranslator::getResponse);
    }

    public boolean isShowStackTrace() {
        return showStackTrace;
    }

    public void setShowStackTrace(boolean showStackTrace) {
        this.showStackTrace = showStackTrace;
    }
}
