package com.inmaytide.orbit.auth.provider;

import com.inmaytide.orbit.auth.token.AuthenticatedToken;
import com.inmaytide.orbit.domain.sys.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

public class FormAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = Objects.toString(authentication.getCredentials(), null);

        Map<Object, Object> details = (Map<Object, Object>) authentication.getDetails();
        String clientId = Objects.toString(details.get("client"), null);
        Assert.hasText(clientId, "The client value must be not empty");

        return new AuthenticatedToken(new User());
    }

    @Override
    public boolean supports(Class<?> cls) {
        return true;
    }
}
