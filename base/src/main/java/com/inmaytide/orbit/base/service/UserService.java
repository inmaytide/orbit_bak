package com.inmaytide.orbit.base.service;

import com.inmaytide.orbit.base.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> get(Long id);

    Optional<User> getByUsername(String username);

    User save(User user);
}
