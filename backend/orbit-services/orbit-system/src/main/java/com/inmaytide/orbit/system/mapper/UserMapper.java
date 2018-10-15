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

    @Select("select * from public.user where username = #{username}")
    User getByUsername(String username);

    @Select("select code from menu menu " +
            "left join link_role_menu rm on rm.menu_id = menu.id " +
            "left join link_user_role ur on rm.role_id = ur.role_id " +
            "left join public.user u on u.id = ur.user_id " +
            "where u.username = #{username} " +
            "union all " +
            "select code from menu_func func " +
            "left join link_role_func rf on rf.func_id = func.id " +
            "left join link_user_role ur on rf.role_id = ur.role_id " +
            "left join public.user u on u.id = ur.user_id " +
            "where u.username = #{username} ")
    Set<String> listPermissions(String username);

    @Select("<script>" +
            "select * from public.user where 1 = 1 " +
            "<if test=\"conditions.keyword != null and conditions.keyword != ''\">" +
            "and ((name ~ '${conditions.keyword}') or (username ~ '${conditions.keyword}') or (cellphone ~ '${conditions.keyword}')  or (email ~ '${conditions.keyword}'))" +
            "</if>" +
            "<if test=\"conditions.organization != null\">" +
            "and org = to_number(#{conditions.organization}, '9999999999999999999')" +
            "</if>" +
            "<if test=\"conditions.status != null\">" +
            "and status = to_number(#{conditions.status}, '9999999999999999999')" +
            "</if> order by id desc" +
            "</script>")
    List<User> list(@Param("conditions") Map<String, Object> conditions);
}
