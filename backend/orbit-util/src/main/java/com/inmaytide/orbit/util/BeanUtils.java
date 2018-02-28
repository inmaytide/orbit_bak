package com.inmaytide.orbit.util;

public class BeanUtils {

    public static void copyProperties(Object source, Object target, String... properties) {
        Assert.nonNull(source, "Source must not be null");
        Assert.nonNull(target, "Target must not be null");
        Assert.nonEmpty(properties, "At least one property to copy");

        Class<?> cls = source.getClass();
        if (!cls.isInstance(target)) {

        }
    }

}
