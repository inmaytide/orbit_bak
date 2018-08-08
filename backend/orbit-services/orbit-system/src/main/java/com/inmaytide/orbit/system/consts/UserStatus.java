package com.inmaytide.orbit.system.consts;

import java.util.Arrays;
import java.util.Objects;

public enum UserStatus {
    ENABLE(1),
    DISABLED(2),
    DELETED(3);

    private Integer value;

    UserStatus(Integer value) {
        this.value = value;
    }


    public static UserStatus of(final Integer value) {
        return Arrays.stream(values())
                .filter(status -> Objects.equals(status.value, value))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Unkown value for \"User Status\""));
    }

    public Integer getValue() {
        return value;
    }
}
