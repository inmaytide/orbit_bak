package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class UnexpectedUsernameException extends GeneralException {

    public UnexpectedUsernameException() {
        super(ERROR_UNEXPECTED_USERNAME, HttpStatus.FORBIDDEN);
    }
}
