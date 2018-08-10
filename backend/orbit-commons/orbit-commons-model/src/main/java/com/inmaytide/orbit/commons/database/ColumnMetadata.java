package com.inmaytide.orbit.commons.database;

import com.google.common.base.CaseFormat;
import com.inmaytide.orbit.commons.database.annotation.Associate;
import com.inmaytide.orbit.commons.database.annotation.Ignorable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.inmaytide.orbit.commons.database.TableMetadata.BASIC_CLASS;

public class ColumnMetadata {

    public static final List<ColumnMetadata> GENERAL_COLUMNS = new ArrayList<>();

    static {
        GENERAL_COLUMNS.addAll(ColumnMetadata.fetchColumns(BASIC_CLASS));
    }

    private String propertyName;
    private String columnName;
    private boolean isAssociate = false;


    private ColumnMetadata() {

    }

    static ColumnMetadata of(String propertyName, String columnName) {
        ColumnMetadata inst = new ColumnMetadata();
        inst.propertyName = propertyName;
        inst.columnName = columnName;
        return inst;
    }

    private static ColumnMetadata forProperty(Field field) {
        ColumnMetadata column = new ColumnMetadata();
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

    public String whereEquals() {
        if (this.isAssociate) {
            throw new IllegalStateException("Associated column can not be condition for query");
        }
        return String.format("%s = #{%s}", columnName, propertyName);
    }
}
