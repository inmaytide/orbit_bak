package com.inmaytide.orbit.dictionary.handler;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.commons.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class VisitorResolver implements WebFilter {

    private static ThreadLocal<ObjectNode> visitor = new ThreadLocal<>();

    private static final String SERVICE_URL_GET_USER = "/users/{username}";

    @Autowired
    private LoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction;

    public static Optional<ObjectNode> currentVisitor() {
        return Optional.ofNullable(visitor.get());
    }

    public void getUserByUsername(String username) {
        WebClient.builder().baseUrl("http://orbit-system-management/sys")
                .filter(loadBalancerExchangeFilterFunction)
                .build()
                .get()
                .uri(SERVICE_URL_GET_USER, username)
                .retrieve()
                .bodyToMono(ObjectNode.class)
                .subscribe(node -> visitor.set(node))
                .dispose();
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
                    getUserByUsername(node.get("user_name").asText());
                }, () -> {
                    visitor.remove();
                });

        return chain.filter(exchange);
    }

}
