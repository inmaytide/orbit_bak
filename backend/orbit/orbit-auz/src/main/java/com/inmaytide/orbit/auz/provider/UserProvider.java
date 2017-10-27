package com.inmaytide.orbit.auz.provider;

import com.inmaytide.orbit.domain.sys.User;

import java.util.Optional;

/**
 * @author Moss
 * @since October 04, 2017
 */
public interface UserProvider {

    Optional<User> getByUsername(String username);

}
