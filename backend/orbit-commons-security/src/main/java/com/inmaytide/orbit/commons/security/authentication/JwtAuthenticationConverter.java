package com.inmaytide.orbit.commons.security.authentication;

import com.inmaytide.orbit.commons.util.AccessTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class JwtAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    private TokenStore tokenStore;

    public JwtAuthenticationConverter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        return AccessTokenUtils.getValue(exchange.getRequest())
                .map(value -> tokenStore.readAuthentication(value))
                .map(OAuth2Authentication::getUserAuthentication)
                .map(Mono::just)
                .orElse(Mono.empty());
    }
}
