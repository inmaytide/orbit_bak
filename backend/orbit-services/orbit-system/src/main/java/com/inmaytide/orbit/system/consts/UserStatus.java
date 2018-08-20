package com.inmaytide.orbit.system.consts;

import java.util.Arrays;
import java.util.Objects;

public enum UserStatus {
    ENABLE(1),
    OUTWORK(2),
    DISABLED(3),
    VACATION(4);

    private Integer value;

    UserStatus(Integer value) {
        this.value = value;
    }


    public static UserStatus of(final Integer value) {
        return Arrays.stream(values())
                .filter(status -> Objects.equals(status.value, value))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Unkown target for \"User Status\""));
    }

    public Integer getValue() {
        return value;
    }
}
