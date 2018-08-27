package com.inmaytide.orbit.system.consts;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrganizationCategory {
    GROUP(0),
    COMPANY(1),
    DEPARTMENT(2),
    STATION(3);


    private Integer value;

    OrganizationCategory(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }
}
