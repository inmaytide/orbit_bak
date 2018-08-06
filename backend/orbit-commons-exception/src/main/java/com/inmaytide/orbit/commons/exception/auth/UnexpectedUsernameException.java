package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.ResponseException;
import com.inmaytide.orbit.commons.exception.consts.ResponseDefinition;

public class UnexpectedUsernameException extends ResponseException {

    private static final long serialVersionUID = 6262058689974289552L;

    public UnexpectedUsernameException() {
        super(ResponseDefinition.UNEXPECTED_USERNAME);
    }
}
