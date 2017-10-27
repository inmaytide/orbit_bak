package com.inmaytide.orbit.auz.realm;

import com.inmaytide.orbit.auz.token.JwtAuthenticationToken;
import com.inmaytide.orbit.exception.InvalidTokenException;
import com.inmaytide.orbit.auz.util.TokenUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.springframework.stereotype.Component;

@Component
public class JwtRealm extends AbstractRealm {

    public JwtRealm() {
        super();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof JwtAuthenticationToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authenticationToken;
        if (TokenUtils.validate(token.getToken())) {
            return getUserByUsername(token.getUsername())
                    .map(user -> new SimpleAccount(user, token.getToken(), getName()))
                    .orElseThrow(InvalidTokenException::new);
        }
        throw new InvalidTokenException();
    }

}
