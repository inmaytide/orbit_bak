package com.inmaytide.orbit.system.service.impl;

import com.inmaytide.orbit.system.domain.User;
import com.inmaytide.orbit.system.mapper.UserMapper;
import com.inmaytide.orbit.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(mapper.get(id));
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(mapper.getByUsername(username));
    }

    @Override
    public Set<String> listPermissions(String username) {
        return mapper.listPermissions(username);
    }
}
