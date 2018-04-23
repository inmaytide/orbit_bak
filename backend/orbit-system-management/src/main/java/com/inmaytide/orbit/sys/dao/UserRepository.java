package com.inmaytide.orbit.sys.dao;

import com.inmaytide.orbit.sys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    void deleteByIdIn(List<Long> ids);

    @Query(value = "select u from User u where id in (select uId from UserRole where rId = ?1)")
    List<User> findByRole(Long roleId);

}
