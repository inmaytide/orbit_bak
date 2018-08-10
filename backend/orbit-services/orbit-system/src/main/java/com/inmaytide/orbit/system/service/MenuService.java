package com.inmaytide.orbit.system.service;

import com.inmaytide.orbit.system.domain.Menu;

import java.util.List;
import java.util.Optional;

/**
 * @author Moss
 * @since August 04, 2018
 */
public interface MenuService {

    List<Menu> listByUsername(String username);

    Optional<Menu> get(Long id);

    Menu save(Menu menu);
}
