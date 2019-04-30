package com.inmaytide.orbit.base.auth;

import com.inmaytide.orbit.Constants;
import org.springframework.http.HttpCookie;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult.notMatch;

public class AnonymousMatcher {

    private ServerWebExchange exchange;

    public AnonymousMatcher(ServerWebExchange exchange) {
        this.exchange = exchange;
    }


    public Mono<ServerWebExchangeMatcher.MatchResult> match() {
        return getAnonymousToken()
                .filter(Constants.ANONYMOUS_TOKEN_VALUE::equals)
                .map(e -> MatchResult.match())
                .orElse(notMatch());
    }

    private Optional<String> getAnonymousToken() {
        return Optional.of(exchange.getRequest().getCookies())
                .map(e -> e.getFirst(Constants.TOKEN_NAME))
                .map(HttpCookie::getValue);
    }


}
