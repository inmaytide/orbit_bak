package com.inmaytide.orbit.auth.handler;

import com.inmaytide.orbit.auth.token.FormAuthenticatedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since November 13, 2017
 */
@Component
public class LoginHandler {

    @Autowired
    private AuthenticationManager authenticationManager;

    private Authentication login(FormAuthenticatedToken token) {
        return authenticationManager.authenticate(token);
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(FormAuthenticatedToken.class)
                .map(this::login)
                .transform(mono -> ok().body(mono, Authentication.class));
    }

}
