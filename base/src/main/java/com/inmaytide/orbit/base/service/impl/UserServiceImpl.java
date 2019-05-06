package com.inmaytide.orbit.base.service.impl;

import com.inmaytide.orbit.base.domain.User;
import com.inmaytide.orbit.base.repository.UserRepository;
import com.inmaytide.orbit.base.service.UserService;
import com.inmaytide.orbit.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        user.setStatus(UserStatus.NORMAL);
        return repository.save(user);
    }
}
