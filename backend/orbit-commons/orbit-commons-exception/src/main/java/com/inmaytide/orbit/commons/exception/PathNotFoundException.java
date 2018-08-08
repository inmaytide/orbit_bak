package com.inmaytide.orbit.commons.exception;

import com.inmaytide.orbit.commons.exception.consts.ResponseDefinition;

/**
 * @author Moss
 * @since October 03, 2017
 */
public class PathNotFoundException extends ResponseException {

    private static final long serialVersionUID = -8609901351595503054L;

    public PathNotFoundException() {
        super(ResponseDefinition.PATH_NOT_FOUND);
    }

}
