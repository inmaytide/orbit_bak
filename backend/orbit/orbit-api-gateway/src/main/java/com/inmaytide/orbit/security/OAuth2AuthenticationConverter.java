package com.inmaytide.orbit.security;

import com.inmaytide.orbit.commons.consts.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

public class OAuth2AuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    private TokenStore tokenStore;

    public OAuth2AuthenticationConverter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        return getAccessTokenValue(exchange.getRequest())
                .map(value -> tokenStore.readAuthentication(value))
                .map(OAuth2Authentication::getUserAuthentication)
                .map(Mono::just)
                .orElse(Mono.empty());
    }

    private Optional<String> getAccessTokenValue(ServerHttpRequest request) {
        String value = request.getHeaders().getFirst(Constants.HEADER_NAME_AUTHORIZATION);
        if (StringUtils.isBlank(value) || !value.startsWith("Bearer")) {
            value = request.getQueryParams().getFirst(Constants.QUERY_PARAM_NAME_AUTHORIZATION);
        } else {
            value = value.substring(7);
        }
        return Optional.ofNullable(value);
    }
}
