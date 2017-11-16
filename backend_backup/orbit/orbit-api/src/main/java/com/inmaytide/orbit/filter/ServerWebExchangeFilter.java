package com.inmaytide.orbit.filter;

import com.inmaytide.orbit.holder.WebExchangeHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

/**
 * @author Moss
 * @since October 15, 2017
 */
public class ServerWebExchangeFilter implements WebFilter {

    @Override
    @Nonnull
    public Mono<Void> filter(@Nonnull ServerWebExchange exchange, @Nonnull WebFilterChain chain) {
        WebExchangeHolder.set(exchange);
        return chain.filter(exchange);
    }

}
