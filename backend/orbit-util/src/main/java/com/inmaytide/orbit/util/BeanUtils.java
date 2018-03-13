package com.inmaytide.orbit.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanUtils {

    public static Field getField(Class<?> cls, String property) {
        Field field = ReflectionUtils.findField(cls, property);
        Assert.nonNull(field, "Cann't find field. name => [" + property + "]");
        return field;
    }

    public static List<Field> getFields(Class<?> cls, String... properties) {
        return Stream.of(properties)
                .map(property -> getField(cls, property))
                .collect(Collectors.toList());
    }

    private static void copy(Field sourceField, Field targetField, Object source, Object target) {
        try {
            sourceField.trySetAccessible();
            targetField.trySetAccessible();
            Object value = sourceField.get(source);
            targetField.set(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void copy(Field f, Object source, Object target) {
        copy(f, f, source, target);
    }

    public static void copyProperties(Object source, Object target, String... properties) {
        Assert.nonNull(source, "Source must not be null");
        Assert.nonNull(target, "Target must not be null");
        Assert.nonEmpty(properties, "At least one property to copy");

        Class<?> sourceClass = source.getClass();
        List<Field> sourceFields = getFields(source.getClass(), properties);

        // if source and target has same classes
        if (sourceClass.isInstance(target)) {
            sourceFields.forEach(f -> copy(f, source, target));
        } else {  // source and target has different class
            Class<?> targetClass = target.getClass();
            sourceFields.forEach(sf -> {
                Field targetField = getField(targetClass, sf.getName());
                copy(sf, targetField, source, target);
            });
        }

    }

}
