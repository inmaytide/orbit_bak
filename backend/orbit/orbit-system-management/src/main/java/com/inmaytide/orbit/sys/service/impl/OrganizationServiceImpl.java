package com.inmaytide.orbit.sys.service.impl;

import com.inmaytide.orbit.sys.dao.OrganizationRepository;
import com.inmaytide.orbit.sys.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationRepository getRepository() {
        return organizationRepository;
    }
}
