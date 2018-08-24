package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.ResponseException;
import com.inmaytide.orbit.commons.exception.consts.ThrowableDefinition;

public class ExpiredTokenException extends ResponseException {
    public ExpiredTokenException() {
        super(ThrowableDefinition.EXPIRED_TOKEN);
    }
}
