package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.ResponseException;
import com.inmaytide.orbit.commons.exception.consts.ResponseDefinition;

public class ExpiredTokenException extends ResponseException {
    public ExpiredTokenException() {
        super(ResponseDefinition.EXPIRED_TOKEN);
    }
}
