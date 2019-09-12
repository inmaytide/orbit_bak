package com.inmaytide.orbit.uaa.auth.service;

import com.inmaytide.orbit.uaa.service.PermissionService;
import com.inmaytide.orbit.uaa.service.RoleService;
import com.inmaytide.orbit.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Stream;

public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getByUsername(username)
                .map(u -> User.withUsername(u.getUsername()).password(u.getPassword()).disabled(u.disabled()).authorities(getAuthorities(u.getId())).build())
                .orElseThrow(() -> new UsernameNotFoundException("Wrong username"));

    }

    private String[] getAuthorities(Long id) {
        return Stream.concat(
                roleService.getCodesByUser(id).parallelStream().map(code -> "ROLE_" + code),
                permissionService.getCodesByUser(id).parallelStream()
        ).toArray(String[]::new);
    }

}
