package com.inmaytide.orbit.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class FormAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -6763744804955471314L;

    private String captcha;

    private String captchaCacheName;

    public FormAuthenticationToken(Object principal, Object credentials, String captcha, String captchaCacheName) {
        super(principal, credentials);
        this.captcha = captcha;
        this.captchaCacheName = captchaCacheName;
    }

    public String getCaptcha() {
        return captcha;
    }

    public String getCaptchaCacheName() {
        return captchaCacheName;
    }
}
