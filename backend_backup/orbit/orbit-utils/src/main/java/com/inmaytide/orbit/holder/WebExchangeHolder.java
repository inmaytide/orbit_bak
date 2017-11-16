package com.inmaytide.orbit.holder;

import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author Moss
 * @since October 15, 2017
 */
public class WebExchangeHolder {

    private static final ThreadLocal<ServerWebExchange> webExchangeHolder = new NamedThreadLocal<>("Exchange context");

    public static void set(@Nullable ServerWebExchange exchange) {
        webExchangeHolder.set(exchange);
    }

    public static ServerWebExchange get() {
        ServerWebExchange exchange = webExchangeHolder.get();
        if (exchange == null) {
            throw new IllegalStateException("No thread-bound request found");
        }
        return exchange;
    }

}
