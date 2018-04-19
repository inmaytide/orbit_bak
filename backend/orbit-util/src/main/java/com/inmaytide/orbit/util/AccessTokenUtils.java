package com.inmaytide.orbit.util;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;

import static com.inmaytide.orbit.constant.Constants.HEADER_NAME_INNER_TOKEN;
import static com.inmaytide.orbit.constant.Constants.INNER_TOKEN;

public class AccessTokenUtils {

    private static final String HEADER_NAME_AUTHORIZATION = "Authorization";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String BEARER_TYPE = "Bearer";

    public static Optional<String> getValue(ServerHttpRequest request) {
        String value = request.getHeaders().getFirst(HEADER_NAME_AUTHORIZATION);
        if (StringUtils.hasLength(value) && value.startsWith(BEARER_TYPE)) {
            value = value.substring(BEARER_TYPE.length() + 1);
        } else {
            value = request.getQueryParams().getFirst(ACCESS_TOKEN);
        }
        return Optional.ofNullable(value);
    }

    public static Optional<String> getInnerValue(ServerWebExchange exchange) {
        return Optional.ofNullable(exchange.getRequest().getHeaders().getFirst(HEADER_NAME_INNER_TOKEN));
    }

    public static boolean matchInnerToken(ServerWebExchange exchange) {
        return getInnerValue(exchange)
                .map(INNER_TOKEN::equals)
                .orElse(false);
    }

}
