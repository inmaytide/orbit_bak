package com.inmaytide.orbit.sys.dao.link;

import com.inmaytide.orbit.sys.domain.link.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    void deleteByRIdIn(List<Long> roleIds);

    void deleteByRIdAndUIdIn(Long roleId, List<Long> uIds);

    void deleteByUIdIn(List<Long> uIds);

}
