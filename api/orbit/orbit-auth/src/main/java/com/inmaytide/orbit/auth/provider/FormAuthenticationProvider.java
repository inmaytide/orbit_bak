package com.inmaytide.orbit.auth.provider;

import com.inmaytide.orbit.auth.exception.LockedAccountException;
import com.inmaytide.orbit.auth.exception.UnknownAccountException;
import com.inmaytide.orbit.auth.service.UserService;
import com.inmaytide.orbit.auth.token.FormAuthenticatedToken;
import com.inmaytide.orbit.domain.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;
import java.util.Optional;

public class FormAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FormAuthenticatedToken token = (FormAuthenticatedToken) authentication;
        String username = Objects.toString(token.getPrincipal(), null);
        String password = Objects.toString(authentication.getCredentials(), null);
        Optional<User> user = userService.getByUsername(username);

        user.map(this::isLocked)
                .orElseThrow(UnknownAccountException::new);

        token.setAuthenticated(true);
        //token.setUser(user);
        return token;
    }

    private User isLocked(User user) {
        if (user.isLocked()) {
            throw new LockedAccountException();
        }
        return user;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Objects.equals(cls, FormAuthenticatedToken.class);
    }


}
