package com.inmaytide.orbit.commons.database;

import com.inmaytide.orbit.commons.database.annotation.Table;
import com.inmaytide.orbit.commons.domain.AbstractEntity;

import java.util.*;

public class TableMetadata {

    static final Class<?> BASIC_CLASS = AbstractEntity.class;

    private String tableName;

    private ColumnMetadata pkColumn = ColumnMetadata.of("id", "id");
    private ColumnMetadata versionColumn = ColumnMetadata.of("version", "version");

    private Set<ColumnMetadata> columns;

    private TableMetadata() {
    }

    public static TableMetadata forEntity(Class<?> entityClass) {
        if (!Objects.equals(entityClass.getSuperclass(), BASIC_CLASS)) {
            throw new IllegalArgumentException(String.format("Only support the subclass of [%s]", BASIC_CLASS.getName()));
        }
        TableMetadata inst = new TableMetadata();
        inst.tableName = fetchTableName(entityClass);
        inst.columns = ColumnMetadata.fetchColumns(entityClass);
        return inst;
    }

    private static String fetchTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            return entityClass.getAnnotation(Table.class).value();
        }
        return entityClass.getSimpleName().toLowerCase();
    }

    public String getTableName() {
        return tableName;
    }

    public Set<ColumnMetadata> getColumns() {
        return columns;
    }

    public ColumnMetadata getPkColumn() {
        return pkColumn;
    }

    public ColumnMetadata getVersionColumn() {
        return versionColumn;
    }
}
