package com.inmaytide.orbit.sys.dao.link;

import com.inmaytide.orbit.domain.sys.link.UserOrganization;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

import java.util.List;

public interface UserOrganizationRepository extends MybatisRepository<UserOrganization, Long> {

    void deleteByUIdIn(List<Long> uIds);

}
