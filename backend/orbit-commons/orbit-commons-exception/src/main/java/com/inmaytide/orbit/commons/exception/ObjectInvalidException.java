package com.inmaytide.orbit.commons.exception;

import static com.inmaytide.orbit.commons.exception.consts.ThrowableDefinition.OBJ_INVALID;

public class ObjectInvalidException extends ResponseException {

    public ObjectInvalidException() {
        super(OBJ_INVALID);
    }

}
