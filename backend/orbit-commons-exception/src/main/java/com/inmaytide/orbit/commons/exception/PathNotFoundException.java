package com.inmaytide.orbit.commons.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Moss
 * @since October 03, 2017
 */
public class PathNotFoundException extends GeneralException {

    private static final long serialVersionUID = -8609901351595503054L;

    public PathNotFoundException() {
        super(ERROR_PATH_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

}
