package com.inmaytide.orbit.commons.security.matcher;

import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;

import static com.inmaytide.orbit.commons.Constants.HEADER_NAME_INSIDE_TOKEN;
import static com.inmaytide.orbit.commons.Constants.INSIDE_TOKEN;

public class InsideTokenMatcher {

    private ServerWebExchange exchange;

    public InsideTokenMatcher(ServerWebExchange exchange) {
        this.exchange = exchange;
    }

    public boolean match() {
        return getTokenValue().map(INSIDE_TOKEN::equals).orElse(false);
    }

    private Optional<String> getTokenValue() {
        return Optional.ofNullable(exchange.getRequest().getHeaders().getFirst(HEADER_NAME_INSIDE_TOKEN));
    }


}
