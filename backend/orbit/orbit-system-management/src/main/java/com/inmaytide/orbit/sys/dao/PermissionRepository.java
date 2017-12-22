package com.inmaytide.orbit.sys.dao;

import com.inmaytide.orbit.sys.domain.Permission;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "select distinct(p.code) as code from sys_permission p " +
            "left join sys_role_permission rp on rp.p_id = p.id " +
            "left join sys_role r on rp.r_id = r.id " +
            "left join sys_user_role ur on ur.r_id = r.id " +
            "left join sys_user u on u.id = ur.u_id " +
            "where u.username = ?1", nativeQuery = true)
    List<String> findCodesByUsername(String username);

    @Query(value = "select * from sys_permission p " +
            "left join sys_role_permission rp on rp.p_id = p.id " +
            "left join sys_role r on rp.r_id = r.id " +
            "left join sys_user_role ur on ur.r_id = r.id " +
            "left join sys_user u on u.id = ur.u_id " +
            "where u.username = ?1 and p.category = ?2 " +
            "order by sort", nativeQuery = true)
    List<Permission> findByUsername(String username, Long category);

    @Query(value = "select ifnull(max(sort), -1) + 1 as sort from sys_permission", nativeQuery = true)
    Integer getSort();

    @Query(value = "select p from sys_permission p where id in (select p_id from sys_role_permission where r_id = ?1)", nativeQuery = true)
    List<Permission> findByRole(Long roleId);

    Integer countByCodeAndIdNot(String code, Long id);

    List<Permission> findByParent(Long parent, Sort sort);

    List<Permission> findByCategory(Long category, Sort sort);
}
