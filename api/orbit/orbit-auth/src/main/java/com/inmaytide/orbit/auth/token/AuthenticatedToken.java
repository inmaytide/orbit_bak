package com.inmaytide.orbit.auth.token;

import com.inmaytide.orbit.domain.sys.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticatedToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -6763744804955471314L;

    private User user;

    public AuthenticatedToken(User user) {
        super(null);
        super.setAuthenticated(true);
        this.user = user;
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}
