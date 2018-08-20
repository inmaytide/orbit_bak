package com.inmaytide.orbit.system.service;

import com.inmaytide.orbit.commons.service.BasicService;
import com.inmaytide.orbit.system.domain.User;

import java.util.Optional;
import java.util.Set;

public interface UserService extends BasicService<User> {

    Optional<User> getByUsername(String username);

    Set<String> listPermissions(String username);

}
