package com.inmaytide.orbit.commons.security.matcher;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.inmaytide.orbit.commons.consts.Constants.HEADER_NAME_INSIDE_TOKEN;
import static com.inmaytide.orbit.commons.consts.Constants.INSIDE_TOKEN;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult.notMatch;

public class InsideTokenMatcher {

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
