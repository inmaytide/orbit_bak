package com.inmaytide.orbit.commons.exception;

import com.inmaytide.orbit.commons.exception.consts.ResponseDefinition;

public class ObjectNotFoundException extends ResponseException {
    private static final long serialVersionUID = -2502972592465592867L;

    public ObjectNotFoundException() {
        super(ResponseDefinition.OBJ_NOT_FOUND);
    }
}
