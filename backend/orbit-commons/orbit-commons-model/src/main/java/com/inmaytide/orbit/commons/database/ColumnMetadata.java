package com.inmaytide.orbit.commons.database;

import com.google.common.base.CaseFormat;
import com.inmaytide.orbit.commons.database.annotation.Associate;
import com.inmaytide.orbit.commons.database.annotation.Ignorable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import static com.inmaytide.orbit.commons.database.TableMetadata.BASIC_CLASS;

public class ColumnMetadata {

    public static final List<ColumnMetadata> GENERAL_COLUMNS = new ArrayList<>();
    public static final ColumnMetadata PK = getGeneral("id");
    public static final ColumnMetadata VERSION = getGeneral("version");
    public static final ColumnMetadata CREATOR = getGeneral("creator");
    public static final ColumnMetadata CREATE_TIME = getGeneral("createTime");
    public static final ColumnMetadata UPDATER = getGeneral("updater");
    public static final ColumnMetadata UPDATE_TIME = getGeneral("updateTime");

    private String propertyName;
    private String columnName;
    private Field field;
    private boolean isAssociate = false;

    private ColumnMetadata() {

    }

    private static ColumnMetadata getGeneral(String propertyName) {
        if (GENERAL_COLUMNS.isEmpty()) {
            GENERAL_COLUMNS.addAll(ColumnMetadata.fetchColumns(BASIC_CLASS));
        }
        return GENERAL_COLUMNS.stream()
                .filter(metadata -> Objects.equals(propertyName, metadata.getPropertyName()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static ColumnMetadata forProperty(Field field) {
        field.trySetAccessible();
        ColumnMetadata column = new ColumnMetadata();
        column.field = field;
        column.propertyName = field.getName();
        column.columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column.getPropertyName());
        if (field.isAnnotationPresent(Associate.class)) {
            Associate associate = field.getAnnotation(Associate.class);
            column.columnName = String.format("(select %s from %s where id = %s) as %s",
                    associate.value(),
                    associate.table(),
                    associate.associate(),
                    column.columnName);
            column.isAssociate = true;
        }
        return column;
    }

    static Set<ColumnMetadata> fetchColumns(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()) && !f.isAnnotationPresent(Ignorable.class))
                .map(ColumnMetadata::forProperty)
                .collect(Collectors.toSet());
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getColumnName() {
        return columnName;
    }

    public Field getField() {
        return field;
    }

    public boolean isAssociate() {
        return isAssociate;
    }

    public String getValueColumn() {
        return String.format("#{%s}", getPropertyName());
    }

    public String getSetColumn() {
        return String.format("%s = #{%s}", columnName, propertyName);
    }

    public String whereEquals() {
        if (this.isAssociate) {
            throw new IllegalStateException("Associated column can not be condition for query");
        }
        return String.format("%s = #{%s}", columnName, propertyName);
    }

    public boolean wasNull(Object instance) {
        if (!getField().getDeclaringClass().isInstance(instance)) {
            throw new IllegalArgumentException("The class of instance is not match to columns class");
        }
        return ReflectionUtils.getField(getField(), instance) == null;
    }
}
