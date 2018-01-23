package com.inmaytide.orbit.dictionary.handler;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.commons.util.JsonUtils;
import com.inmaytide.orbit.dictionary.client.UserClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    private static final Logger log = LoggerFactory.getLogger(VisitorResolver.class);

    private static ThreadLocal<String> visitor = new ThreadLocal<>();

    @Autowired
    private UserClient client;

    @Autowired
    private VisitorResolver instance;

    public static Optional<ObjectNode> currentVisitor() {
        return Optional.ofNullable(visitor.get()).map(JsonUtils::readJsonString);
    }

    @Cacheable("visitor-json")
    public String getUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        log.debug("Get user with username [{}]", username);
        return client.getUserByUsername(username)
                .map(ObjectNode::toString)
                .orElse(null);
    }

    private Optional<String> getAccessToken(ServerHttpRequest request) {
        String value = request.getHeaders().getFirst(Constants.HEADER_NAME_AUTHORIZATION);
        if (StringUtils.isBlank(value) || !value.startsWith(Constants.BEARER_TYPE)) {
            value = request.getQueryParams().getFirst(Constants.ACCESS_TOKEN);
        } else {
            value = value.substring(7);
        }
        return Optional.ofNullable(value);
    }

    private void setVisitor(String token) {
        Jwt jwt = JwtHelper.decode(token);
        ObjectNode node = JsonUtils.readJsonString(jwt.getClaims());
        visitor.set(instance.getUserByUsername(node.get("user_name").asText()));
    }

    private void clearVisitor() {
        visitor.remove();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        getAccessToken(exchange.getRequest())
                .ifPresentOrElse(this::setVisitor, this::clearVisitor);
        return chain.filter(exchange);
    }

}
