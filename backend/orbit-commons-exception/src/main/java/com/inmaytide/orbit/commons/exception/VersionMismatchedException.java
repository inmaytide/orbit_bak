package com.inmaytide.orbit.commons.exception;

import org.springframework.http.HttpStatus;

/**
 * The version property does not match while the data is going to modify or remove.
 *
 * @author Moss
 * @since September 3, 2017
 */
public class VersionMismatchedException extends GeneralException {

    private static final long serialVersionUID = -22605139663792344L;

    public VersionMismatchedException() {
        super(ERROR_VERSION_MISMATCHED, HttpStatus.CONFLICT);
    }
}
