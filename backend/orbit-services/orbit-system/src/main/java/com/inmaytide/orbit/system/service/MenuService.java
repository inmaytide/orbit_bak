package com.inmaytide.orbit.system.service;

import com.inmaytide.orbit.commons.service.BasicService;
import com.inmaytide.orbit.system.domain.Menu;

import java.util.List;

/**
 * @author Moss
 * @since August 04, 2018
 */
public interface MenuService extends BasicService<Menu> {

    List<Menu> listByUsername(String username);

    /**
     * Determine if a code is already present
     * @param code
     * @param ignore  ignored the menu if it's id equal
     * @return
     */
    boolean exist(String code, Long ignore);

}
