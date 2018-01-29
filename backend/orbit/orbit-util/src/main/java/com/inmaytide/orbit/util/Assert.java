package com.inmaytide.orbit.util;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Supplier;

public class Assert {

    public static void hasText(String text) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException();
        }
    }

    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasText(String text, Supplier<RuntimeException> supplier) {
        if (!StringUtils.hasText(text)) {
            throw supplier.get();
        }
    }

    public static void nonNull(Object o, Supplier<RuntimeException> supplier) {
        if (Objects.isNull(o)) {
            throw supplier.get();
        }
    }

    public static void isTrue(boolean bool, Supplier<RuntimeException> supplier) {
        if (!bool) {
            throw supplier.get();
        }
    }

}
