package com.inmaytide.orbit.commons.exception;

import org.springframework.http.HttpStatus;

public class NotAuthenticatedException extends HttpResponseException {

    private static final String DEFAULT_CODE = "err_not_authenticated";

    public NotAuthenticatedException() {
        super(HttpStatus.UNAUTHORIZED, DEFAULT_CODE);
    }

    public NotAuthenticatedException(Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, DEFAULT_CODE, cause.getMessage());
    }

}
