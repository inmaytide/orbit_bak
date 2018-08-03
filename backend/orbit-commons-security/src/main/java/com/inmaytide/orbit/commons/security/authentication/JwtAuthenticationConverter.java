package com.inmaytide.orbit.commons.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class JwtAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    private static final String HEADER_NAME_AUTHORIZATION = "Authorization";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String BEARER_TYPE = "Bearer";

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationConverter.class);

    private TokenStore tokenStore;

    public JwtAuthenticationConverter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        return extract(exchange.getRequest()).map(Mono::just).orElse(Mono.empty());
    }

    private Optional<Authentication> extract(ServerHttpRequest request) {
        return extractToken(request)
                .map(tokenStore::readAuthentication)
                .map(OAuth2Authentication::getUserAuthentication);
    }

    private Optional<String> extractToken(ServerHttpRequest request) {
        Optional<String> token = extractHeaderToken(request);
        if (!token.isPresent()) {
            logger.debug("Token not found in headers. Trying request parameters.");
            token = Optional.ofNullable(request.getQueryParams().getFirst(ACCESS_TOKEN));
            if (!token.isPresent()) {
                logger.debug("Token not found in request parameters.");
            }
        }
        return token;
    }

    private Optional<String> extractHeaderToken(ServerHttpRequest request) {
        List<String> values = request.getHeaders().get(HEADER_NAME_AUTHORIZATION);
        if (CollectionUtils.isEmpty(values)) {
            return Optional.empty();
        }
        return values.stream()
                .filter(value -> StringUtils.hasText(value) && value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))
                .map(value -> value.substring(BEARER_TYPE.length()).trim())
                .findFirst().or(Optional::empty);

    }
}
