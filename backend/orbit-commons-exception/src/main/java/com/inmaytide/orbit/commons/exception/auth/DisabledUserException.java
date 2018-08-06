package com.inmaytide.orbit.commons.exception.auth;

import com.inmaytide.orbit.commons.exception.ResponseException;
import com.inmaytide.orbit.commons.exception.consts.ResponseDefinition;

public class DisabledUserException extends ResponseException {

    private static final long serialVersionUID = -2544984344793406147L;

    public DisabledUserException() {
        super(ResponseDefinition.DISABLED_USER);
    }
}
