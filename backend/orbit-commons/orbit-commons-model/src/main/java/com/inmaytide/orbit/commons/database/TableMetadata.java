package com.inmaytide.orbit.commons.database;

import com.google.common.base.CaseFormat;
import com.inmaytide.orbit.commons.database.annotation.Table;
import com.inmaytide.orbit.commons.domain.AbstractEntity;

import java.util.Objects;
import java.util.Set;

public class TableMetadata {

    static final Class<?> BASIC_CLASS = AbstractEntity.class;

    private String tableName;
    private Class<?> entityClass;
    private Set<ColumnMetadata> columns;

    private TableMetadata() {
    }

    public static TableMetadata forEntity(Class<?> entityClass) {
        if (!Objects.equals(entityClass.getSuperclass(), BASIC_CLASS)) {
            throw new IllegalArgumentException(String.format("Only support the subclass of [%s]", BASIC_CLASS.getName()));
        }
        TableMetadata inst = new TableMetadata();
        inst.entityClass = entityClass;
        inst.tableName = fetchTableName(entityClass);
        inst.columns = ColumnMetadata.fetchColumns(entityClass);
        return inst;
    }

    private static String fetchTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            return entityClass.getAnnotation(Table.class).value();
        }
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName());
    }

    public String getTableName() {
        return tableName;
    }

    public Set<ColumnMetadata> getColumns() {
        return columns;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
