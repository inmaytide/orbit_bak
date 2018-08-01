package com.inmaytide.orbit.commons.id;

import java.util.UUID;

public class UUIDGenerator {

    public static String generate() {
        return String.join("", UUID.randomUUID().toString().split("-"));
    }

}
