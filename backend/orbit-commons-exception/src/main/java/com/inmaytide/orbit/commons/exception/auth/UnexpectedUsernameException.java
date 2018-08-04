package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class UnexpectedUsernameException extends GeneralException {

    private static final long serialVersionUID = 6262058689974289552L;

    public UnexpectedUsernameException() {
        super(ERROR_UNEXPECTED_USERNAME, HttpStatus.UNAUTHORIZED);
    }
}
