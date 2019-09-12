package com.inmaytide.orbit.uaa.repository;

import com.inmaytide.orbit.uaa.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query(nativeQuery = true, value = "select code from role where id in (select role_id from user_role where user_id=?1)")
    List<String> getCodesByUser(Long id);
}
