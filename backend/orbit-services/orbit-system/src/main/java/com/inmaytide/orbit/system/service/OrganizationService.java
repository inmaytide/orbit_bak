package com.inmaytide.orbit.system.service;

import com.inmaytide.orbit.commons.service.BasicService;
import com.inmaytide.orbit.system.domain.Organization;

public interface OrganizationService extends BasicService<Organization> {

    boolean exist(String code, Long ignore);

}
