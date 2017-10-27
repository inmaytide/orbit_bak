package com.inmaytide.orbit.dao.sys;

import com.inmaytide.orbit.domain.sys.Permission;
import org.springframework.data.domain.Sort;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends MybatisRepository<Permission, Long> {

    @Query(value = "findCodesByUsername")
    List<String> findCodesByUsername(@Param("username") String username);

    @Query(value = "findByUsername")
    List<Permission> findByUsername(@Param("username") String username, @Param("category") String category);

    @Query(value = "getSort")
    Integer getSort();

    @Query(value = "findByRole")
    List<Permission> findByRole(@Param("roleId") Long roleId);

    Integer countByCodeAndIdNot(String code, Long id);

    List<Permission> findByParent(Long parent, Sort sort);
}
