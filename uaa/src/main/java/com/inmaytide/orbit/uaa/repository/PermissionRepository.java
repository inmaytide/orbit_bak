package com.inmaytide.orbit.uaa.repository;

import com.inmaytide.orbit.uaa.domain.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {


}
