package com.inmaytide.orbit.sys.dao;

import com.inmaytide.orbit.sys.dao.specification.UserSpecification;
import com.inmaytide.orbit.sys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    void deleteByIdIn(List<Long> ids);

    @Query("select user from sys_user user where id in (select u_id from sys_user_role where r_id = ?1)")
    List<User> findByRole(Long roleId);

}
