package com.inmaytide.orbit.commons.util;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;

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

}
