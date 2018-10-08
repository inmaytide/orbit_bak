package com.inmaytide.orbit.system.mapper;

import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.system.domain.Function;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface FunctionMapper extends BasicMapper<Function> {

    @Select("select * from menu_func where menu_id = #{menuId}")
    List<Function> listByMenuId(@Param("menuId") Long menuId);

    @Select("select count(1) from menu_func where code = #{params.code} and id != #{params.ignore}")
    int countByCode(@Param("params") Map<String, Object> params);

}
