package com.inmaytide.orbit.system.mapper;

import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface UserMapper extends BasicMapper<User> {

    @Select("select * from sys_user where username = #{username}")
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

    @Select("<script>" +
            "select * from sys_user where 1 = 1 " +
            "<if test=\"conditions.keyword != null and conditions.keyword != ''\">" +
            "and (instr(name, #{conditions.keyword}) or instr(username, #{conditions.keyword}) or instr(cellphone, #{conditions.keyword}))" +
            "</if>" +
            "<if test=\"conditions.organization != null\">" +
            "and org = #{conditions.organization}" +
            "</if>" +
            "</script>")
    List<User> list(@Param("conditions") Map<String, Object> conditions);
}
