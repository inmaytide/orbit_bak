package com.inmaytide.orbit.system.service.impl;

import com.inmaytide.orbit.commons.service.AbstractService;
import com.inmaytide.orbit.system.domain.Function;
import com.inmaytide.orbit.system.mapper.FunctionMapper;
import com.inmaytide.orbit.system.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunctionServiceImpl extends AbstractService<Function> implements FunctionService {

    @Autowired
    private FunctionMapper mapper;

    @Override
    public List<Function> listByMenuId(Long menuId) {
        return getMapper().listByMenuId(menuId);
    }

    @Override
    public FunctionMapper getMapper() {
        return mapper;
    }

    @Override
    public boolean exist(String code, Long ignore) {
        return exist(code, ignore, mapper::countByCode);
    }
}
