package com.inmaytide.orbit.office.excel;

import com.inmaytide.orbit.office.excel.annotation.Comment;
import com.inmaytide.orbit.office.excel.annotation.ExcelTemplate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Excel操作通用工具方法
 *
 * @author Moss
 * @since September 08, 2017
 */
public class ExcelUtils {

    public static List<Field> getFields(Class<? extends Serializable> cls) {
        return Stream.of(cls.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Comment.class))
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());
    }

    public static Comment getComment(Field field) {
        if (!field.isAnnotationPresent(Comment.class)) {
            throw new RuntimeException();
        }
        return field.getAnnotation(Comment.class);
    }

    public static ExcelTemplate getTemplate(Class<? extends Serializable> cls) {
        return cls.getAnnotation(ExcelTemplate.class);
    }

}
