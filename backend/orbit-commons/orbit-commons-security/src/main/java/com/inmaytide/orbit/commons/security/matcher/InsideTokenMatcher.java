package com.inmaytide.orbit.commons.security.matcher;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult.notMatch;

public class InsideTokenMatcher {

    private static final String HEADER_NAME_INSIDE_TOKEN = "inside-service-token";
    private static final String INSIDE_TOKEN = "MmY3ZDA4YjY1MDA2OGUzZDA2YWVmZTY3MTc0NjFlOTc0ZGEzZTJiNTc3ZDM4NDJhNmUyMDRjMGZjMDQ2YTM1ZQ==";

    private ServerWebExchange exchange;

    public InsideTokenMatcher(ServerWebExchange exchange) {
        this.exchange = exchange;
    }

    public Mono<ServerWebExchangeMatcher.MatchResult> match() {
        return getTokenValue()
                .map(INSIDE_TOKEN::equals)
                .map(bool -> bool ? ServerWebExchangeMatcher.MatchResult.match() : notMatch())
                .orElse(notMatch());
    }

    private Optional<String> getTokenValue() {
        return Optional.ofNullable(exchange.getRequest().getHeaders().getFirst(HEADER_NAME_INSIDE_TOKEN));
    }


}
