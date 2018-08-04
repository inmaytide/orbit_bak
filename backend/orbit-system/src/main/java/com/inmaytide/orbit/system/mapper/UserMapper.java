package com.inmaytide.orbit.system.mapper;

import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface UserMapper extends BasicMapper {

    String TABLE_NAME = "sys_user";

    String COLUMNS = "name, username, password, status, brithday, education, email, qq, wechat, telephone, cellphone, avatar, remark, " + COMMON_COLUMNS;

    @Select("select " + COLUMNS + " from " + TABLE_NAME + " where id = #{id}")
    User get(Long id);

    @Select("select " + COLUMNS + " from " + TABLE_NAME + " where username = #{username}")
    User getByUsername(String username);

    @Select("select code from sys_menu menu " +
            "left join link_role_menu rm on rm.menu_id = menu.id " +
            "left join link_user_role ur on rm.role_id = ur.role_id " +
            "left join sys_user u on u.id = ur.user_id " +
            "where u.username = #{username} " +
            "union all " +
            "select code from sys_menu_func func " +
            "left join link_role_func rf on rf.func_id = func.id " +
            "left join link_user_role ur on rf.role_id = ur.role_id " +
            "left join sys_user u on u.id = ur.user_id " +
            "where u.username = #{username} ")
    Set<String> listPermissions(String username);

}
