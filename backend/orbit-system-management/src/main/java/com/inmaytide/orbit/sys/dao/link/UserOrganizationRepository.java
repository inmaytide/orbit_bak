package com.inmaytide.orbit.sys.dao.link;

import com.inmaytide.orbit.sys.domain.link.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

public interface UserOrganizationRepository extends JpaRepository<UserOrganization, Long> {

    void deleteByUIdIn(List<Long> uIds);

    Stream<UserOrganization> streamByOIdIn(List<Long> orgs);

}
