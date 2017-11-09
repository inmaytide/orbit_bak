package com.inmaytide.orbit.exception;

/**
 * The version does not match when the data is modified.
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

    public VersionMatchedException(Throwable e) {
        super(e);
    }

}
