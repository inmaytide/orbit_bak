package com.inmaytide.orbit.dao.sys;

import com.inmaytide.orbit.domain.sys.link.UserRole;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

import java.util.List;

public interface UserRoleRepository extends MybatisRepository<UserRole, String> {

    void deleteByRIdIn(List<Long> roleIds);

    void deleteByRIdAndUIdIn(Long roleId, List<Long> uIds);

}
