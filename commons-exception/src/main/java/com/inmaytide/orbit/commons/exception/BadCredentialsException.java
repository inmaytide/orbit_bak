package com.inmaytide.orbit.commons.exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends HttpResponseException {

    private static final String DEFAULT_CODE = "err_bad_credentials";

    public BadCredentialsException() {
        super(HttpStatus.FORBIDDEN, DEFAULT_CODE);
    }

}
