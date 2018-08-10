package com.inmaytide.orbit.commons.mapper.builder;

import com.inmaytide.orbit.commons.database.TableMetadata;
import com.inmaytide.orbit.commons.database.helper.MetadataHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

public class SqlSourceBuilder {


    public String get(ProviderContext context) {
        TableMetadata metadata = MetadataHelper.getTableMetadata(context);
        return new SQL().SELECT(MetadataHelper.getSelectColumns(metadata))
                .FROM(metadata.getTableName())
                .WHERE(metadata.getPkColumn().whereEquals())
                .toString();
    }

    public String save(ProviderContext context, Object instance) {
        TableMetadata metadata = MetadataHelper.getTableMetadata(context);

        return new SQL().toString();
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
