package com.inmaytide.orbit.commons.exception;

/**
 * The version property does not match while the data is going to modify or remove.
 *
 * @author Moss
 * @since September 3, 2017
 */
public class VersionMatchedException extends RuntimeException {

    private static final long serialVersionUID = -22605139663792344L;

    public VersionMatchedException() {
        super();
    }

    public VersionMatchedException(String message) {
        super(message);
    }

}
