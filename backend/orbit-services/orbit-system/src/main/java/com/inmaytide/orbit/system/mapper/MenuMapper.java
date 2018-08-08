package com.inmaytide.orbit.system.mapper;

import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.system.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Moss
 * @since August 04, 2018
 */
@Mapper
public interface MenuMapper extends BasicMapper {

    String TABLE_NAME = "sys_menu";

    String COLUMNS = "code, name, icon, method, url, description, parent, seq_order seqOrder," + COMMON_COLUMNS;

    @Select("select " + COLUMNS + " from " + TABLE_NAME + " where id = #{id}")
    Menu get(Long id);

    @Select("select menu.id, menu.code, menu.name, menu.icon, menu.method, menu.url, menu.parent, seq_order seqOrder, menu.create_time createTime from sys_menu menu " +
            "left join link_role_menu rm on rm.menu_id = menu.id " +
            "left join link_user_role ur on rm.role_id = ur.role_id " +
            "left join sys_user u on u.id = ur.user_id " +
            "where u.username = #{username} order by seq_order")
    List<Menu> listByUsername(String username);

}
