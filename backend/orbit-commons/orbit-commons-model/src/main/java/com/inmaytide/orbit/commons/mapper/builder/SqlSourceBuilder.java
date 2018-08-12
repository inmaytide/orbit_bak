package com.inmaytide.orbit.commons.mapper.builder;

import com.inmaytide.orbit.commons.database.ColumnMetadata;
import com.inmaytide.orbit.commons.database.TableMetadata;
import com.inmaytide.orbit.commons.database.helper.MetadataHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class SqlSourceBuilder {

    public String get(ProviderContext context) {
        TableMetadata metadata = MetadataHelper.getTableMetadata(context);
        return new SQL().SELECT(MetadataHelper.translateSelectColumns(metadata))
                .FROM(metadata.getTableName())
                .WHERE(ColumnMetadata.PK.whereEquals())
                .toString();
    }

    public String save(Object instance) {
        TableMetadata metadata = MetadataHelper.getTableMetadata(instance);
        List<ColumnMetadata> columnMetadatas = metadata.getColumns()
                .stream()
                .filter(column -> !column.wasNull(instance))
                .collect(Collectors.toList());
        columnMetadatas.add(ColumnMetadata.PK);
        columnMetadatas.add(ColumnMetadata.CREATOR);
        String columns = MetadataHelper.translateColumns(columnMetadatas, ColumnMetadata::getColumnName);
        String values = MetadataHelper.translateColumns(columnMetadatas, ColumnMetadata::getValueColumn);
        return new SQL().INSERT_INTO(metadata.getTableName())
                .VALUES(columns + ", create_time", values + ", now()").toString();
    }

    public String remove(ProviderContext context) {
        TableMetadata metadata = MetadataHelper.getTableMetadata(context);
        return new SQL().DELETE_FROM(metadata.getTableName())
                .WHERE(ColumnMetadata.PK.whereEquals()).toString();
    }

    private String __update(Object instance, Predicate<ColumnMetadata> filter) {
        TableMetadata metadata = MetadataHelper.getTableMetadata(instance);
        SQL sql = new SQL().UPDATE(metadata.getTableName());
        metadata.getColumns().stream()
                .filter(filter)
                .map(ColumnMetadata::getSetColumn)
                .forEach(sql::SET);
        sql.SET(ColumnMetadata.UPDATER.getSetColumn());
        sql.SET(ColumnMetadata.UPDATE_TIME.getColumnName() + " = now()");
        sql.SET(ColumnMetadata.VERSION.getColumnName() + " = version + 1");
        sql.WHERE(ColumnMetadata.PK.whereEquals(), ColumnMetadata.VERSION.whereEquals());
        return sql.toString();
    }

    public String update(Object instance) {
        return __update(instance, columnMetadata -> !columnMetadata.isAssociate());
    }

    public String updateSelective(Object instance) {
        return __update(instance, columnMetadata -> !columnMetadata.isAssociate() && columnMetadata.wasNull(instance));
    }

    public String all(ProviderContext context) {
        TableMetadata metadata = MetadataHelper.getTableMetadata(context);
        return new SQL()
                .SELECT(MetadataHelper.translateSelectColumns(metadata))
                .FROM(metadata.getTableName())
                .toString();
    }


}
