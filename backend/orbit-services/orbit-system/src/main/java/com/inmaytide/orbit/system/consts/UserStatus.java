package com.inmaytide.orbit.system.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

public enum UserStatus {
    ENABLE(1),
    OUTWORK(2),
    VACATION(3),
    DISABLED(99);

    @JsonValue
    private Integer value;

    UserStatus(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static UserStatus of(final Integer value) {
        return Arrays.stream(values())
                .filter(status -> Objects.equals(status.value, value))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Unkown target for \"User Status\""));
    }

    public Integer getValue() {
        return value;
    }
}
