package com.inmaytide.orbit.commons.exception;

import static com.inmaytide.orbit.commons.exception.consts.ResponseDefinition.OBJ_INVALID;

public class ObjectInvalidException extends ResponseException {

    public ObjectInvalidException() {
        super(OBJ_INVALID);
    }

}
