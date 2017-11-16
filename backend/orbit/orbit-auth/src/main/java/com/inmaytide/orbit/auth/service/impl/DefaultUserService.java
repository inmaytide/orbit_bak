package com.inmaytide.orbit.auth.service.impl;

import com.inmaytide.orbit.auth.service.UserService;
import com.inmaytide.orbit.domain.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private static final String SERVICE_URL_GET_USER_BY_USERNAME = "http://orbit-system-management/users/{username}";

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public Optional<User> getByUsername(String username) {
        User user = restTemplate.getForObject(SERVICE_URL_GET_USER_BY_USERNAME, User.class, username);
        return Optional.ofNullable(user);
    }
}
