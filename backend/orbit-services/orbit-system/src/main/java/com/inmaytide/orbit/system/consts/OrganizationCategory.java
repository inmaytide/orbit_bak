package com.inmaytide.orbit.system.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

public enum OrganizationCategory {
    GROUP(0),
    COMPANY(1),
    DEPARTMENT(2),
    STATION(3);


    private Integer value;

    OrganizationCategory(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static OrganizationCategory of(final Integer value) {
        return Arrays.stream(values())
                .filter(status -> Objects.equals(status.value, value))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Unkown target for \"Organization Category\""));
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }
}
