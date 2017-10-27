package com.inmaytide.orbit.dao.sys;

import com.inmaytide.orbit.domain.sys.link.RolePermission;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

import java.util.List;

public interface RolePermissionRepository extends MybatisRepository<RolePermission, Long> {

    void deleteByRIdIn(List<Long> roleIds);

    void deleteByPIdIn(List<Long> permssionIds);

    //void insertInBatch(List<RolePermission> rolePermissions);
}
