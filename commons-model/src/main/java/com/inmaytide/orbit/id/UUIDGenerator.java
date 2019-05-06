package com.inmaytide.orbit.id;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator<String> {

    @Override
    public String generate() {
        return String.join("", UUID.randomUUID().toString().split("-"));
    }

}
