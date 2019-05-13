package com.inmaytide.orbit.uaa.repository;

import com.inmaytide.orbit.uaa.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {


}
