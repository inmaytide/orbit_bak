package com.inmaytide.orbit.commons.database.helper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.inmaytide.orbit.commons.database.ColumnMetadata;
import com.inmaytide.orbit.commons.database.TableMetadata;
import com.inmaytide.orbit.commons.mapper.BasicMapper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.inmaytide.orbit.commons.database.ColumnMetadata.GENERAL_COLUMNS;

public class MetadataHelper {

    private static Cache<String, TableMetadata> CACHE = CacheBuilder.newBuilder().build();

    private static String cacheName(Class<?> cls) {
        return cls.getName();
    }

    private static Class<?> getEntityClass(ProviderContext context) {
        Type[] types = context.getMapperType().getGenericInterfaces();
        for (Type parent : types) {
            ResolvableType parentType = ResolvableType.forType(parent);
            if (parentType.getRawClass() == BasicMapper.class) {
                return parentType.getGeneric(0).getRawClass();
            }
        }
        throw new IllegalArgumentException(String.format("The [%s] is not a implementation of [%s]", context.getMapperType().getName(), BasicMapper.class.getName()));
    }

    private static TableMetadata getTableMetadata(Class<?> cls) {
        String cacheName = cacheName(cls);
        TableMetadata metadata = CACHE.getIfPresent(cacheName);
        if (metadata == null) {
            metadata = TableMetadata.forEntity(cls);
            CACHE.put(cacheName, metadata);
        }
        return metadata;
    }

    public static TableMetadata getTableMetadata(ProviderContext context) {
        return getTableMetadata(getEntityClass(context));
    }

    public static TableMetadata getTableMetadata(Object instance) {
        Assert.notNull(instance, "The instance cannt not be null");
        return getTableMetadata(instance.getClass());
    }


    public static String translateSelectColumns(TableMetadata metadata) {
        StringJoiner joiner = new StringJoiner(", ");
        Stream.concat(metadata.getColumns().stream(), GENERAL_COLUMNS.stream())
                .map(ColumnMetadata::getColumnName)
                .forEach(joiner::add);
        return joiner.toString();
    }

    public static String translateColumns(Collection<ColumnMetadata> metadatas, Function<ColumnMetadata, String> translator) {
        Assert.notNull(translator, "Translator cannot be null");
        Assert.notEmpty(metadatas, "The columns to be translate cannot be empty");
        StringJoiner joiner = new StringJoiner(",");
        metadatas.stream().map(translator).forEach(joiner::add);
        return joiner.toString();
    }

}
