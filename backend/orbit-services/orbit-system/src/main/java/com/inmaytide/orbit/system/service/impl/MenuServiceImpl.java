package com.inmaytide.orbit.system.service.impl;

import com.inmaytide.orbit.commons.id.IdGenerator;
import com.inmaytide.orbit.system.domain.Menu;
import com.inmaytide.orbit.system.mapper.MenuMapper;
import com.inmaytide.orbit.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Moss
 * @since August 04, 2018
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper mapper;

    @Autowired
    private IdGenerator<Long> generator;

    @Override
    public List<Menu> listByUsername(String username) {
        return mapper.listByUsername(username);
    }

    @Override
    public Optional<Menu> get(Long id) {
        return Optional.ofNullable(mapper.get(id));
    }

    @Override
    public Menu save(Menu menu) {
        menu.setId(generator.generate());
        mapper.save(menu);
        return menu;
    }
}
