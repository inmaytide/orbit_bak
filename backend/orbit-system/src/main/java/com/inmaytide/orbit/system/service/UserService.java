package com.inmaytide.orbit.system.service;

import com.inmaytide.orbit.system.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> get(Long id);

    Optional<User> getByUsername(String username);

}
