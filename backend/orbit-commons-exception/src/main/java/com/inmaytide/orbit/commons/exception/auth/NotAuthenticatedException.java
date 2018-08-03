package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class NotAuthenticatedException extends GeneralException {

    public NotAuthenticatedException() {
        super(ERROR_NOT_AUTHENTICATED, HttpStatus.UNAUTHORIZED);
    }

}
