package com.inmaytide.orbit.auz.filter;

import com.inmaytide.orbit.auz.handler.DefaultFilterHandlers;
import com.inmaytide.orbit.auz.util.SessionUtils;
import com.inmaytide.orbit.auz.token.JwtAuthenticationToken;
import com.inmaytide.orbit.exception.InvalidTokenException;
import com.inmaytide.orbit.auz.util.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author Moss
 * @since October 18, 2017
 */
public class JwtAuthenticationFilter implements WebFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private PathsMatcher pathsMatcher;

    public JwtAuthenticationFilter(PathsMatcher pathsMatcher) {
        this.pathsMatcher = pathsMatcher;
    }

    /**
     * Determine whether the request is allowed to be anonymous
     * @return if allowed return true, otherwise return false
     */
    private boolean isAllowAnonymous(ServerHttpRequest request) {
        String filter = pathsMatcher.getMatchFilter(request);
        return DefaultFilterHandlers.valueOf(filter) == DefaultFilterHandlers.anon;
    }

    private boolean isLoggedAttempt(ServerHttpRequest request) {
        return getAuthzHeader(request).isPresent();
    }

    private Optional<String> getAuthzHeader(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst(AUTHORIZATION_HEADER));
    }

    private JwtAuthenticationToken createToken(ServerHttpRequest request) {
        Optional<String> token = getAuthzHeader(request);
        return token.map(this::createToken).orElseThrow(InvalidTokenException::new);
    }

    private JwtAuthenticationToken createToken(String token) {
        try {
            Claims claims = TokenUtils.getClaims(token);
            return new JwtAuthenticationToken(claims.getSubject(), token);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    @Override
    @Nonnull
    public Mono<Void> filter(@Nonnull ServerWebExchange exchange, @Nonnull WebFilterChain chain) {
        // If it's an allowable anonymous request or an authenticated request, handled by another filter
        if (SessionUtils.getSubject().isAuthenticated() || isAllowAnonymous(exchange.getRequest())) {
            return chain.filter(exchange);
        }
        // If there is an authorization header, try to login with it
        if (isLoggedAttempt(exchange.getRequest())) {
            JwtAuthenticationToken token = createToken(exchange.getRequest());
            SessionUtils.getSubject().login(token);
        }
        return chain.filter(exchange);
    }

}
