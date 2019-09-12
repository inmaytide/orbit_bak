package com.inmaytide.orbit.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum UserStatus {
    NORMAL(1),
    ON_HOLIDAY(2),
    ON_BUSINESS(3),
    DISABLED(99);

    private Integer value;

    UserStatus(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static UserStatus valueOf(Integer value) {
        return Stream.of(values()).filter(e -> e.getValue().equals(value))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }
}
