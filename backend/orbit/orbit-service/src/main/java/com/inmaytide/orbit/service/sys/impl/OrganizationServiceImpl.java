package com.inmaytide.orbit.service.sys.impl;

import com.inmaytide.orbit.dao.sys.OrganizationRepository;
import com.inmaytide.orbit.domain.sys.Organization;
import com.inmaytide.orbit.service.sys.OrganizationService;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationRepository getRepository() {
        return organizationRepository;
    }
}
