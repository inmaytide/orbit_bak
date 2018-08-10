package com.inmaytide.orbit.commons.mapper;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.commons.mapper.builder.SqlSourceBuilder;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

public interface BasicMapper<T extends AbstractEntity> {

    @InsertProvider(type = SqlSourceBuilder.class, method = "save")
    int save(T t);

    @DeleteProvider(type = SqlSourceBuilder.class, method = "remove")
    int remove(Long id);

    @UpdateProvider(type = SqlSourceBuilder.class, method = "update")
    int update(T t);

    @UpdateProvider(type = SqlSourceBuilder.class, method = "updateSelective")
    int updateSelective(T t);

    @SelectProvider(type = SqlSourceBuilder.class, method = "get")
    T get(Long id);

    @SelectProvider(type = SqlSourceBuilder.class, method = "all")
    List<T> all();



}
