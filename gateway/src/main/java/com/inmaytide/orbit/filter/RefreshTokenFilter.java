package com.inmaytide.orbit.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.service.UaaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author luomiao
 * @since 2019/09/27
 */
@Component
public class RefreshTokenFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenFilter.class);

    private static final int REFRESH_SECONDS_BEFORE_EXPIRATION = 30;

    private static final String HEADER_NAME_ACCESS_TOKEN = "Authorization";

    private static final String HEADER_NAME_REFRESH_TOKEN = "x-refresh-token";

    private static final String BODY_NAME_ACCESS_TOKEN = "access_token";

    private static final String BODY_NAME_REFRESH_TOKEN = "refresh_token";

    private static final String ACCESS_TOKEN_TYPE = "Bearer";

    private static final Map<String, String> REFRESH_REQUEST_BODY = new HashMap<>();

    static {
        REFRESH_REQUEST_BODY.put("grant_type", "refresh_token");
        REFRESH_REQUEST_BODY.put("client_id", "orbit");
        REFRESH_REQUEST_BODY.put("scope", "all");
        REFRESH_REQUEST_BODY.put("client_secret", "59a84cbf83227a35");
    }

    private final UaaService uaaService;

    private final ObjectMapper objectMapper;

    @Autowired
    public RefreshTokenFilter(UaaService uaaService, ObjectMapper objectMapper) {
        this.uaaService = uaaService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (needRefreshToken(exchange)) {
            refresh(exchange);
        }
        return chain.filter(exchange);
    }

    private void refresh(ServerWebExchange exchange) {
        String refreshToken = exchange.getRequest().getHeaders().getFirst(HEADER_NAME_REFRESH_TOKEN);
        if (refreshToken == null) {
            return;
        }
        Map<String, String> requestBody = new HashMap<>(REFRESH_REQUEST_BODY);
        requestBody.put(BODY_NAME_REFRESH_TOKEN, refreshToken);
        Map<String, Object> res = uaaService.refreshToken(requestBody);
        exchange.getRequest().getHeaders().set(HEADER_NAME_ACCESS_TOKEN, ACCESS_TOKEN_TYPE + " " + Objects.toString(res.get(BODY_NAME_ACCESS_TOKEN), ""));
        exchange.getResponse().getHeaders().set(HEADER_NAME_ACCESS_TOKEN, Objects.toString(res.get(BODY_NAME_ACCESS_TOKEN), ""));
        exchange.getResponse().getHeaders().set(HEADER_NAME_REFRESH_TOKEN, Objects.toString(res.get(BODY_NAME_REFRESH_TOKEN), ""));
    }

    /**
     * 验证是否需要刷新token
     *
     * @param exchange
     * @return
     */
    private boolean needRefreshToken(ServerWebExchange exchange) {
        return getAccessToken(exchange).map(this::needRefreshToken).orElse(false);
    }

    private Optional<String> getAccessToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HEADER_NAME_ACCESS_TOKEN);
        if (token == null) {
            return Optional.empty();
        }
        token = token.replace(ACCESS_TOKEN_TYPE, "").trim();
        return Optional.of(token);
    }

    private boolean needRefreshToken(String token) {
        try {
            String[] array = Pattern.compile("\\.").split(token);
            String value = new String(Base64Utils.decodeFromString(array[1]));
            ObjectNode node = objectMapper.readValue(value, ObjectNode.class);
            return (node.get("exp").asLong() - Instant.now().getEpochSecond()) < REFRESH_SECONDS_BEFORE_EXPIRATION;
        } catch (Exception e) {
            LOGGER.error("Invalid access token, {}, {}", token, e.getMessage());
            return false;
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
