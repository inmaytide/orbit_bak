package com.inmaytide.orbit.auth.token;

import com.inmaytide.orbit.domain.sys.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class FormAuthenticatedToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -6763744804955471314L;

    private String captcha;

    private String captchaCacheName;

    private User user;

    public FormAuthenticatedToken(Object principal, Object credentials, String captcha, String captchaCacheName) {
        super(principal, credentials);
        this.captcha = captcha;
        this.captchaCacheName = captchaCacheName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCaptcha() {
        return captcha;
    }

    public String getCaptchaCacheName() {
        return captchaCacheName;
    }
}
