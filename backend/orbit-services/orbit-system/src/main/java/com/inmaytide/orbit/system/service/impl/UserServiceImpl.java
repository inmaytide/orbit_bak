package com.inmaytide.orbit.system.service.impl;

import com.inmaytide.orbit.commons.service.AbstractService;
import com.inmaytide.orbit.system.domain.User;
import com.inmaytide.orbit.system.mapper.UserMapper;
import com.inmaytide.orbit.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    private UserMapper mapper;


    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(getMapper().getByUsername(username));
    }

    @Override
    public Set<String> listPermissions(String username) {
        return getMapper().listPermissions(username);
    }

    @Override
    public UserMapper getMapper() {
        return mapper;
    }

}
