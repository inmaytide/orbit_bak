package com.inmaytide.orbit.auth.service;

import com.inmaytide.orbit.domain.sys.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getByUsername(String username);

}
