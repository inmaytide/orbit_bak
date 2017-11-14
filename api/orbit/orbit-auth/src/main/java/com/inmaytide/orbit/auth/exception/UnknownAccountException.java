package com.inmaytide.orbit.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class UnknownAccountException extends AuthenticationException {

    private static final long serialVersionUID = -2735363315342733838L;

    public UnknownAccountException(String message) {
        super(message);
    }
}
