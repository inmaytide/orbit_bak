package com.inmaytide.orbit.system.service;

import com.inmaytide.orbit.commons.service.BasicService;
import com.inmaytide.orbit.commons.util.Assert;
import com.inmaytide.orbit.system.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

public interface UserService extends BasicService<User> {

    String INITIAL_PASSWORD = "123456";
    PasswordEncoder DEFAULT_PASSWORD_ENCODER = new BCryptPasswordEncoder(10);


    Optional<User> getByUsername(String username);

    Set<String> listPermissions(String username);

    void changePassword(User user);

    boolean exist(User user);

    default void assertNotExist(User user) {
        Assert.isTrue(!exist(user), "Username is existed");
    }

    default PasswordEncoder getPasswordEncoder() {
        return DEFAULT_PASSWORD_ENCODER;
    }

}
