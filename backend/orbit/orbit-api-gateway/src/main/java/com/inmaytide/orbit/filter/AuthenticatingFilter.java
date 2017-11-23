package com.inmaytide.orbit.filter;

import com.inmaytide.orbit.commons.consts.Constants;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class AuthenticatingFilter implements WebFilter {

    private Optional<String> getAuthzHeader(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst(Constants.HEADER_NAME_AUTHORIZATION));
    }

    private boolean isLoggedAttempt(ServerHttpRequest request) {
        return getAuthzHeader(request).map(value -> value.startsWith("Bearer")).orElse(false)
                || request.getQueryParams().getFirst(Constants.QUERY_PARAM_NAME_AUTHORIZATION) != null;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (isLoggedAttempt(exchange.getRequest())) {

        }
        return null;
    }

}
