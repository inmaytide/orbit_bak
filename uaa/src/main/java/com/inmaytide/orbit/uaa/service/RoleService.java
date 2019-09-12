package com.inmaytide.orbit.uaa.service;

import com.inmaytide.orbit.uaa.domain.Role;

import java.util.List;
import java.util.Optional;

/**
 * @author luomiao
 * @since 2019/09/11
 */
public interface RoleService {

    List<String> getCodesByUser(Long id);

    Optional<Role> get(Long id);

    Optional<Role> create(Role inst);

    Optional<Role> modify(Role inst);

}
