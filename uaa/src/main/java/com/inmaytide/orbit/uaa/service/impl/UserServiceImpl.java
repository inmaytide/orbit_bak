package com.inmaytide.orbit.uaa.service.impl;

import com.inmaytide.orbit.commons.exception.BadCredentialsException;
import com.inmaytide.orbit.enums.UserStatus;
import com.inmaytide.orbit.uaa.domain.User;
import com.inmaytide.orbit.uaa.repository.UserRepository;
import com.inmaytide.orbit.uaa.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User create(User user) {
        getAuthenticatedUser().map(User::getId).ifPresent(user::setCreator);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.NORMAL);
        return repository.save(user);
    }

    @Override
    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        String name = Objects.toString(token.getTokenAttributes().get("user_name"), "");
        if (StringUtils.isBlank(name)) {
            throw new BadCredentialsException();
        }
        return getByUsername(name);
    }
}
