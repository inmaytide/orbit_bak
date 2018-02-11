package com.inmaytide.orbit.exception;

/**
 * @author Moss
 * @since October 03, 2017
 */
public class PathNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8609901351595503054L;

    public PathNotFoundException() {
        super();
    }

    public PathNotFoundException(String message) {
        super(message);
    }
}
