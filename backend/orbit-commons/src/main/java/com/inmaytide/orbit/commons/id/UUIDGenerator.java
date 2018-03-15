package com.inmaytide.orbit.commons.id;

import java.util.UUID;

public class UUIDGenerator {

    public static String generate() {
        String value = UUID.randomUUID().toString();
        return value.substring(0, 8) + value.substring(9, 13) +
                value.substring(14, 18) + value.substring(19, 23) + value.substring(24);
    }

}
