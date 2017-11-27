package com.inmaytide.orbit.security;

import com.inmaytide.orbit.exception.handler.ThrowableTranslator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Moss
 * @since November 26, 2017
 */
public class FailedAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        String path = exchange.getRequest().getPath().pathWithinApplication().value();
        return Mono.just(e)
                .transform(mono -> ThrowableTranslator.translate(mono, path))
                .flatMap(translator -> translator.write(exchange.getResponse()));
    }

}
