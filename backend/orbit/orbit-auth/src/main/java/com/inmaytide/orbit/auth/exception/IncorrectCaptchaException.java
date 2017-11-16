package com.inmaytide.orbit.auth.exception;


import org.springframework.security.core.AuthenticationException;

public class IncorrectCaptchaException extends AuthenticationException {

    private static final long serialVersionUID = 2917999752814351528L;

    public IncorrectCaptchaException(String message) {
        super(message);
    }

}
