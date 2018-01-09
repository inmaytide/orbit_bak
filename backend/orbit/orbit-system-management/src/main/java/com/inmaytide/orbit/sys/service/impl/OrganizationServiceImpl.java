package com.inmaytide.orbit.sys.service.impl;

import com.inmaytide.orbit.sys.dao.OrganizationRepository;
import com.inmaytide.orbit.sys.domain.Organization;
import com.inmaytide.orbit.sys.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "sort");
    public static final Long ROOT_ID = -1L;

    @Autowired
    private OrganizationRepository repository;

    @Override
    public List<Organization> listByParent(Long parent) {
        if (parent == null) {
            parent = ROOT_ID;
        }
        log.debug("Query organizations by parent [{}]", parent);
        return getRepository().findByParent(parent, DEFAULT_SORT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Organization insert(Organization organization) {
        organization.setSort(getRepository().getSort());
        return getRepository().save(organization);
    }

    @Override
    public OrganizationRepository getRepository() {
        return repository;
    }
}
