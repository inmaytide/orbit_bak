package com.inmaytide.orbit.sys.filter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.commons.util.JsonUtils;
import com.inmaytide.orbit.sys.domain.User;
import com.inmaytide.orbit.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class VisitorResolver implements WebFilter {

    private static ThreadLocal<User> visitor = new ThreadLocal<>();

    private UserService userService;

    public VisitorResolver(UserService userService) {
        this.userService = userService;
    }

    public static Optional<User> currentVisitor() {
        User user = visitor.get();
        if (user == null) {
            throw new IllegalStateException("Unauthorized access, please access the system through normal channels.");
        }
        return Optional.of(user);
    }

    private Optional<String> getAccessTokenValue(ServerHttpRequest request) {
        String value = request.getHeaders().getFirst(Constants.HEADER_NAME_AUTHORIZATION);
        if (StringUtils.isBlank(value) || !value.startsWith(Constants.BEARER_TYPE)) {
            value = request.getQueryParams().getFirst(Constants.ACCESS_TOKEN);
        } else {
            value = value.substring(7);
        }
        return Optional.ofNullable(value);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        getAccessTokenValue(exchange.getRequest())
                .ifPresentOrElse(value -> {
                    Jwt jwt = JwtHelper.decode(value);
                    ObjectNode node = JsonUtils.readJsonString(jwt.getClaims());
                    String username = node.get("user_name").asText();
                    userService.getByUsername(username).ifPresent(user -> visitor.set(user));
                }, () -> {
                    visitor.remove();
                });

        return chain.filter(exchange);
    }

}
