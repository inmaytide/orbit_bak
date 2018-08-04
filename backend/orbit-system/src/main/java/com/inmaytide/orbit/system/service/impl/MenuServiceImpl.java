package com.inmaytide.orbit.system.service.impl;

import com.inmaytide.orbit.system.domain.Menu;
import com.inmaytide.orbit.system.mapper.MenuMapper;
import com.inmaytide.orbit.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Moss
 * @since August 04, 2018
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper mapper;

    @Override
    public List<Menu> listByUsername(String username) {
        return mapper.listByUsername(username);
    }
}
