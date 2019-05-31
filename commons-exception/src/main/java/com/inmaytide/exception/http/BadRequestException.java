package com.inmaytide.orbit.commons.exception.http;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpResponseException {

    private static final String DEFAULT_CODE = "err_bad_request";

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, DEFAULT_CODE);
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, DEFAULT_CODE, message);
    }

}
