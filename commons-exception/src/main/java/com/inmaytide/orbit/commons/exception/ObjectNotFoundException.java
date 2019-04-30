package com.inmaytide.orbit.commons.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends HttpResponseException {

    private static final String DEFAULT_CODE = "err_object_not_found";

    public ObjectNotFoundException() {
        super(HttpStatus.NOT_FOUND, DEFAULT_CODE);
    }

}
