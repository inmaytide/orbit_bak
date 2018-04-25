package com.inmaytide.orbit.util;

import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;

import static com.inmaytide.orbit.constant.Constants.HEADER_NAME_INSIDE_TOKEN;
import static com.inmaytide.orbit.constant.Constants.INSIDE_TOKEN;

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
