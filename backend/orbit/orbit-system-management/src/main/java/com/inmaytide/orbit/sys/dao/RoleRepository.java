package com.inmaytide.orbit.sys.dao;

import com.inmaytide.orbit.sys.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("")
    List<String> findCodesByUsername(@Param("username") String username);

    Page<Role> findByCodeLikeOrNameLike(String code, String name, Pageable pageable);

    Integer countByCodeAndIdNot(String code, Long id);
}
