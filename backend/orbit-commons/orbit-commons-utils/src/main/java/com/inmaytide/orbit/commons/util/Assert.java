package com.inmaytide.orbit.commons.util;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public class Assert {

    public static void hasText(String text) {
        if (text == null || text.trim().length() == 0) {
            throw new IllegalArgumentException();
        }
    }

    public static void hasText(String text, String message) {
        if (text == null || text.trim().length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasText(String text, Supplier<RuntimeException> supplier) {
        if (text == null || text.trim().length() == 0) {
            throw supplier.get();
        }
    }

    public static void nonEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void nonEmpty(String[] arr, String message) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void nonNull(Object o, String message) {
        if (Objects.isNull(o)) {
            throw new IllegalArgumentException(message);
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
