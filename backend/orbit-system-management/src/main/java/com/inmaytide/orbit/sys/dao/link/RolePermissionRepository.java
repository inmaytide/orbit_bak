package com.inmaytide.orbit.sys.dao.link;

import com.inmaytide.orbit.sys.domain.link.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    void deleteByRIdIn(List<Long> roleIds);

    void deleteByPIdIn(List<Long> permssionIds);

}
