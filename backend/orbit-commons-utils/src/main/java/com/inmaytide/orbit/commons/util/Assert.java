package com.inmaytide.orbit.commons.util;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
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

    public static void nonEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
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
