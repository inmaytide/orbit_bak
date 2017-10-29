package com.inmaytide.orbit.consts;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Moss
 * @since October 29, 2017
 */
public enum AttachmentStatus {

    TEMPORARY(0), FORMAL(1);

    private Integer value;

    AttachmentStatus(Integer value) {
        this.value = value;
    }

    public static AttachmentStatus valueOf(Integer value) {
        return Stream.of(values())
                .filter(status -> Objects.equals(status.getValue(), value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Integer getValue() {
        return value;
    }
}
