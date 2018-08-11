package com.inmaytide.orbit.commons.mapper.builder;

import com.inmaytide.orbit.commons.database.ColumnMetadata;
import com.inmaytide.orbit.commons.database.TableMetadata;
import com.inmaytide.orbit.commons.database.helper.MetadataHelper;
import com.inmaytide.orbit.commons.domain.AbstractEntity;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        AbstractEntity entity = (AbstractEntity) instance;
        entity.setCreateTime(LocalDateTime.now());
        List<ColumnMetadata> columnMetadatas = metadata.getColumns()
                .stream()
                .filter(column -> !column.wasNull(instance))
                .collect(Collectors.toList());
        columnMetadatas.add(ColumnMetadata.PK);
        columnMetadatas.add(ColumnMetadata.CREATOR);
        columnMetadatas.add(ColumnMetadata.CREATE_TIME);


        String columns = MetadataHelper.translateColumns(columnMetadatas, ColumnMetadata::getColumnName);
        String values = MetadataHelper.translateColumns(columnMetadatas, ColumnMetadata::getValueColumn);
        return new SQL().INSERT_INTO(metadata.getTableName())
                .VALUES(columns, values).toString();
    }

    public String remove(ProviderContext context) {

        return new SQL().toString();
    }

    public String update(ProviderContext context) {

        return new SQL().toString();
    }

    public String updateSelective(ProviderContext context) {

        return new SQL().toString();
    }

    public String all(ProviderContext context) {

        return new SQL().toString();
    }


}
