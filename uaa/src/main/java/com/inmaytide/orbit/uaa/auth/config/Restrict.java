package com.inmaytide.orbit.uaa.auth.config;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public enum Restrict {

    NORMAL(0, 0),
    CAPTCHE(2, 0),
    FORBIDDEN_5(5, 5),
    FORBIDDEN_10(8, 15),
    FORBIDDEN_30(10, 30),
    FORBIDDEN(15, 365 * 24 * 60 * 10);

    private Integer lower;

    private Integer duration;

    Restrict(Integer lower, Integer duration) {
        this.lower = lower;
        this.duration = duration;
    }

    public Integer getLower() {
        return lower;
    }

    public Integer getDuration() {
        return duration;
    }

    public static Restrict suitable(Integer value) {
        return Stream.of(values())
                .filter(v -> Objects.equals(value, v.getLower()))
                .findFirst()
                .orElse(NORMAL);
    }
}
