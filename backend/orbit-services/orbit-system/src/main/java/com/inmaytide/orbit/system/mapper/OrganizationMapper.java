package com.inmaytide.orbit.system.mapper;

import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.system.domain.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface OrganizationMapper extends BasicMapper<Organization> {


    @Select("select count(1) from organization where code = #{params.code} and id != #{params.ignore}")
    int countByCode(@Param("params") Map<String, Object> params);

}
