package com.inmaytide.orbit.auz.filter;

import com.inmaytide.orbit.auz.handler.DefaultFilterHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

/**
 * @author Moss
 * @since October 04, 2017
 */
public class AuthenticatingFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthenticatingFilter.class);

    private PathsMatcher pathsMatcher;

    public AuthenticatingFilter(PathsMatcher pathsMatcher) {
        this.pathsMatcher = pathsMatcher;
    }

    @Override
    @Nonnull
    public Mono<Void> filter(@Nonnull ServerWebExchange exchange, @Nonnull WebFilterChain chain) {
        String filter = pathsMatcher.getMatchFilter(exchange.getRequest());
        DefaultFilterHandlers
                .valueOf(filter)
                .getFilterHandler()
                .handle(filter);
        return chain.filter(exchange);
    }

}
