package com.inmaytide.orbit.security.commons;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class OAuth2AuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication token) {
        return Mono.justOrEmpty(token)
                .publishOn(Schedulers.elastic())
                .switchIfEmpty(Mono.error(new AccessTokenRequiredException(new BaseOAuth2ProtectedResourceDetails())))
                .filter(Authentication::isAuthenticated);
    }

}
