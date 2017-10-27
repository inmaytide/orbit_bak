package com.inmaytide.orbit.auz.realm;

import com.inmaytide.orbit.auz.cache.SimpleByteSource;
import com.inmaytide.orbit.auz.util.Md5Utils;
import com.inmaytide.orbit.auz.provider.CaptchaProvider;
import com.inmaytide.orbit.auz.token.FormAuthenticationToken;
import com.inmaytide.orbit.exception.IncorrectCaptchaException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Component
public class FormRealm extends AbstractRealm {

    @Resource
    private CaptchaProvider captchaProvider;

    public FormRealm() {
        super();
        setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof FormAuthenticationToken;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Assert.notNull(authenticationToken, "AuthenticationToken method argument cannot be null.");
        FormAuthenticationToken token = (FormAuthenticationToken) authenticationToken;
        String username = token.getUsername();
        Assert.hasText(username, "Null username are not allowed by this realm");
        captchaProvider.validation(token.getCaptcha(), token.getCaptchaKey(), IncorrectCaptchaException::new);
        return getUserByUsername(username)
                .map(user -> new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt(username), getName()))
                .orElseThrow(UnknownAccountException::new);
    }

    private ByteSource credentialsSalt(String username) {
        return new SimpleByteSource(Md5Utils.salt(username));
    }

}
