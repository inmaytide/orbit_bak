package com.inmaytide.orbit.system.service.impl;

import com.inmaytide.orbit.commons.service.AbstractService;
import com.inmaytide.orbit.system.domain.Organization;
import com.inmaytide.orbit.system.mapper.OrganizationMapper;
import com.inmaytide.orbit.system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl extends AbstractService<Organization> implements OrganizationService {

    @Autowired
    private OrganizationMapper mapper;

    @Override
    public boolean exist(String code, Long ignore) {
        return exist(code, ignore, mapper::countByCode);
    }

    @Override
    public OrganizationMapper getMapper() {
        return mapper;
    }
}
