package com.inmaytide.orbit.commons.security.authentication;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import reactor.core.publisher.Mono;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication token) {
        return Mono.justOrEmpty(token)
                .switchIfEmpty(Mono.error(new AccessTokenRequiredException(new BaseOAuth2ProtectedResourceDetails())))
                .filter(Authentication::isAuthenticated)
                .doOnSuccess(ThreadLocalAuthenticationHolder::setAuthentication)
                .doOnError(e -> ThreadLocalAuthenticationHolder.resetAuthentication());
    }

}
