package com.inmaytide.orbit.sys.dao;

import com.inmaytide.orbit.sys.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select distinct(r.code) as code from sys_role r " +
            "left join sys_user_role ur on ur.r_id = r.id " +
            "left join sys_user u on u.id = ur.u_id " +
            "where u.username = ?1", nativeQuery = true)
    List<String> findCodesByUsername(String username);

    Page<Role> findByCodeLikeOrNameLike(String code, String name, Pageable pageable);

    Integer countByCodeAndIdNot(String code, Long id);

    Integer deleteByIdIn(List<Long> ids);
}
