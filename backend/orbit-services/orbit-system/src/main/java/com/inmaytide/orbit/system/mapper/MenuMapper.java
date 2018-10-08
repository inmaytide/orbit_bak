package com.inmaytide.orbit.system.mapper;

import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.system.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Moss
 * @since August 04, 2018
 */
@Mapper
public interface MenuMapper extends BasicMapper<Menu> {

    @Select("select menu.* from menu menu " +
            "left join link_role_menu rm on rm.menu_id = menu.id " +
            "left join link_user_role ur on rm.role_id = ur.role_id " +
            "left join public.user u on u.id = ur.user_id " +
            "where u.username = #{username} order by seq_order")
    List<Menu> listByUsername(String username);

    @Select("select count(1) from menu where code = #{params.code} and id != #{params.ignore}")
    int countByCode(@Param("params") Map<String, Object> params);

}
