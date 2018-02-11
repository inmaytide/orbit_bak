package com.inmaytide.orbit.sys.service;

import com.inmaytide.orbit.sys.dao.OrganizationRepository;
import com.inmaytide.orbit.sys.domain.Organization;

import java.util.List;

public interface OrganizationService extends BasicService<OrganizationRepository, Organization> {

    List<Organization> listByParent(Long parent);

    Organization insert(Organization organization);

}
