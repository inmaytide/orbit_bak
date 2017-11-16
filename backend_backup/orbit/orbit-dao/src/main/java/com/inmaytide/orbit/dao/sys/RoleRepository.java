package com.inmaytide.orbit.dao.sys;

import com.inmaytide.orbit.domain.sys.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends MybatisRepository<Role, Long> {

    @Query(value = "findCodesByUsername")
    List<String> findCodesByUsername(@Param("username") String username);

    Page<Role> findByCodeLikeOrNameLike(String code, String name, Pageable pageable);

    Integer countByCodeAndIdNot(String code, Long id);
}
