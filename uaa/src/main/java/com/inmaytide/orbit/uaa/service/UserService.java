package com.inmaytide.orbit.uaa.service;

import com.inmaytide.orbit.uaa.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> get(Long id);

    Optional<User> getByUsername(String username);

    User create(User user);

    Optional<User> getAuthenticatedUser();
}
