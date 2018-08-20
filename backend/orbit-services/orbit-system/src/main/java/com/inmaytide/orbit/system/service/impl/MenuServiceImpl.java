package com.inmaytide.orbit.system.service.impl;

import com.inmaytide.orbit.commons.mapper.BasicMapper;
import com.inmaytide.orbit.commons.service.AbstractService;
import com.inmaytide.orbit.system.domain.Menu;
import com.inmaytide.orbit.system.mapper.MenuMapper;
import com.inmaytide.orbit.system.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Moss
 * @since August 04, 2018
 */
@Service
public class MenuServiceImpl extends AbstractService<Menu> implements MenuService {

    @Autowired
    private MenuMapper mapper;

    @Override
    public List<Menu> listByUsername(String username) {
        return mapper.listByUsername(username);
    }

    @Override
    public boolean exist(String code, Long ignore) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        return mapper.countByCode(Map.of("code", code, "ignore", Objects.requireNonNullElse(ignore, -1L))) > 0;
    }

    @Override
    public BasicMapper<Menu> getMapper() {
        return mapper;
    }
}
