package com.inmaytide.orbit.commons.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends GeneralException {
    public ObjectNotFoundException() {
        super(ERROR_OBJ_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
