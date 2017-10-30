package com.inmaytide.orbit.dao.sys;

import com.inmaytide.orbit.domain.sys.User;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserRepository extends MybatisRepository<User, Long> {

    User findByUsername(String username);

    void deleteByIdIn(List<Long> ids);

    @Query(value = "findByRole")
    List<User> findByRole(@Param("roleId") Long roleId);

    @Query(value = "findList")
    List<User> findList(@Param("conditions") Map<String, Object> conditions);

    @Query(value = "findCount")
    Integer findCount(@Param("conditions") Map<String, Object> conditions);
}
