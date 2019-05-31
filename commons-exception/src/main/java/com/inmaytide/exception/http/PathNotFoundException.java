package com.inmaytide.orbit.commons.exception.http;

import org.springframework.http.HttpStatus;

public class PathNotFoundException extends HttpResponseException {

    private static final String DEFAULT_CODE = "err_path_not_found";

    public PathNotFoundException() {
        super(HttpStatus.NOT_FOUND, DEFAULT_CODE);
    }

}
