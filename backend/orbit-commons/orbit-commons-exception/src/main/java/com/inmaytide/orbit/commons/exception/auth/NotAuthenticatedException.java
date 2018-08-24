package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.ResponseException;
import com.inmaytide.orbit.commons.exception.consts.ThrowableDefinition;

public class NotAuthenticatedException extends ResponseException {

    private static final long serialVersionUID = 1874797397959626337L;

    public NotAuthenticatedException() {
        super(ThrowableDefinition.NOT_AUTHENTICATED);
    }

}
