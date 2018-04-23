package com.inmaytide.orbit.security.commons;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class OAuth2AuthenticationManager implements ReactiveAuthenticationManager {

    private static final ThreadLocal<Authentication> AUTHENTICATION_THREAD_LOCAL = new ThreadLocal<>();

    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(AUTHENTICATION_THREAD_LOCAL.get());
    }

    @Override
    public Mono<Authentication> authenticate(Authentication token) {
        return Mono.justOrEmpty(token)
                .switchIfEmpty(Mono.error(new AccessTokenRequiredException(new BaseOAuth2ProtectedResourceDetails())))
                .filter(Authentication::isAuthenticated)
                .doOnSuccess(AUTHENTICATION_THREAD_LOCAL::set)
                .doOnError(authenticate -> AUTHENTICATION_THREAD_LOCAL.remove());
    }

}
