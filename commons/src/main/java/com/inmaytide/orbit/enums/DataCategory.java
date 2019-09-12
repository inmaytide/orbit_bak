package com.inmaytide.orbit.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

/**
 * 数据类别
 */
public enum DataCategory {

    SYSTEM(1),
    USER(2);

    private Integer value;

    DataCategory(Integer value) {
        this.value = value;
    }

    @JsonCreator
    public static DataCategory valueOf(Integer value) {
        return Stream.of(values()).filter(e -> e.getValue().equals(value))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

}
