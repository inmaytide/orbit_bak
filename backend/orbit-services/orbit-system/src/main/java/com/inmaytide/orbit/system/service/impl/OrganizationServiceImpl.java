package com.inmaytide.orbit.system.service.impl;

import com.inmaytide.orbit.commons.service.AbstractService;
import com.inmaytide.orbit.system.domain.Organization;
import com.inmaytide.orbit.system.mapper.OrganizationMapper;
import com.inmaytide.orbit.system.service.OrganizationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class OrganizationServiceImpl extends AbstractService<Organization> implements OrganizationService {

    @Autowired
    private OrganizationMapper mapper;


    @Override
    public OrganizationMapper getMapper() {
        return mapper;
    }

    @Override
    public boolean exist(String code, Long ignore) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        return mapper.countByCode(Map.of("code", code, "ignore", Objects.requireNonNullElse(ignore, -1L))) > 0;
    }
}
