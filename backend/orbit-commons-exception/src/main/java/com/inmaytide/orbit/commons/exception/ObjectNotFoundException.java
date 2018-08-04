package com.inmaytide.orbit.commons.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends GeneralException {
    private static final long serialVersionUID = -2502972592465592867L;

    public ObjectNotFoundException() {
        super(ERROR_OBJ_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
