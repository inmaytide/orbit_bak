package com.inmaytide.orbit.filter.visitor;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inmaytide.orbit.util.AccessTokenUtils;
import com.inmaytide.orbit.util.JsonUtils;
import com.inmaytide.orbit.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractVisitorResolver<T extends Serializable> implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(AbstractVisitorResolver.class);

    protected static ThreadLocal<Serializable> visitor = new ThreadLocal<>();

    private UserProvider provider;

    protected AbstractVisitorResolver(@NonNull UserProvider provider) {
        this.provider = provider;
    }

    private void setVisitor(String token) {
        try {
            Jwt jwt = JwtHelper.decode(token);
            ObjectNode node = JsonUtils.readJsonString(jwt.getClaims());
            visitor.set(provider.getByUsername(node.get("user_name").asText()));
        } catch (Exception e) {
            log.error("There is a error in resolve visitor.", e);
            clearVisitor();
        }
    }

    private void clearVisitor() {
        visitor.remove();
    }


    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        AccessTokenUtils.getValue(exchange.getRequest())
                .ifPresentOrElse(this::setVisitor, this::clearVisitor);
        return chain.filter(exchange);
    }
}
