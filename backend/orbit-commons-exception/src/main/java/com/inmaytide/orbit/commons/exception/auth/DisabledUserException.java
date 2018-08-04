package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;

public class DisabledUserException extends GeneralException {

    private static final long serialVersionUID = -2544984344793406147L;

    public DisabledUserException() {
        super(ERROR_DISABLED_USER, HttpStatus.UNAUTHORIZED);
    }
}
