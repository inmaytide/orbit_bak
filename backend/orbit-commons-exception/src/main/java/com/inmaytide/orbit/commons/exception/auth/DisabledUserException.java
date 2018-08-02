package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class DisabledUserException extends GeneralException {

    public DisabledUserException() {
        super(ERROR_DISABLED_USER, HttpStatus.FORBIDDEN);
    }
}
