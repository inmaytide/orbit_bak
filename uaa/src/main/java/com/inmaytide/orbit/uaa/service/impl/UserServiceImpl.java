package com.inmaytide.orbit.uaa.service.impl;

import com.inmaytide.exception.http.BadCredentialsException;
import com.inmaytide.exception.http.ObjectNotFoundException;
import com.inmaytide.orbit.enums.UserStatus;
import com.inmaytide.orbit.uaa.domain.User;
import com.inmaytide.orbit.uaa.repository.UserRepository;
import com.inmaytide.orbit.uaa.service.UserService;
import com.inmaytide.orbit.uaa.utils.AuthenticatedHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    @Override
    @Cacheable(value = "users", key = "#username")
    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    @CacheEvict(value = "users", key = "#user.username")
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.NORMAL);
        return repository.save(user);
    }

    @Override
    @CacheEvict(value = "users", key = "#user.username")
    public User modify(User user) {
        User original = get(user.getId()).orElseThrow(ObjectNotFoundException::new);
        BeanUtils.copyProperties(user, original, "password", "status", "createTime", "creator");
        return repository.save(original);
    }

    @Override
    @CacheEvict(value = "users", key = "#username")
    public void disableUser(String username) {
        getByUsername(username)
                .filter(u -> u.getStatus() != UserStatus.DISABLED)
                .ifPresent(user -> {
                    user.setStatus(UserStatus.DISABLED);
                    repository.save(user);
                });
    }


}
