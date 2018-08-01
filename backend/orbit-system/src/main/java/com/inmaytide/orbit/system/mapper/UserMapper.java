package com.inmaytide.orbit.system.mapper;

import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BasicMapper {

    String TABLE_NAME = "sys_user";

    String COLUMNS = "name, username, password, status, brithday, education, email, qq, wechat, telephone, cellphone, avatar, remark" + ", " + COMMON_COLUMNS;

    @Select("select " + COLUMNS + " from " + TABLE_NAME + " where id = #{id}")
    User get(Long id);

    @Select("select " + COLUMNS + " from " + TABLE_NAME + " where username = #{username}")
    User getByUsername(String username);

}
