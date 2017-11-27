package com.inmaytide.orbit.sys.dao;

import com.inmaytide.orbit.sys.domain.Permission;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("")
    List<String> findCodesByUsername(@Param("username") String username);

    @Query("")
    List<Permission> findByUsername(@Param("username") String username, @Param("category") String category);

    @Query("")
    Integer getSort();

    @Query("")
    List<Permission> findByRole(@Param("roleId") Long roleId);

    Integer countByCodeAndIdNot(String code, Long id);

    List<Permission> findByParent(Long parent, Sort sort);

    List<Permission> findByCategory(String category, Sort sort);
}
