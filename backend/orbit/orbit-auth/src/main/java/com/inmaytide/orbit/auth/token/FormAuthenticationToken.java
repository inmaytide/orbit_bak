package com.inmaytide.orbit.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class FormAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -6763744804955471314L;

    private String captcha;

    public FormAuthenticationToken(Object principal, Object credentials, String captcha) {
        super(principal, credentials);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

}
