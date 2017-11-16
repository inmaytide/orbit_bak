package com.inmaytide.orbit.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class BlankAccountException extends AuthenticationException {

    private static final long serialVersionUID = -5651030624023600550L;

    public BlankAccountException(String message) {
        super(message);
    }
}
