package com.inmaytide.orbit.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inmaytide.orbit.commons.query.Params;
import com.inmaytide.orbit.commons.service.AbstractService;
import com.inmaytide.orbit.system.consts.UserStatus;
import com.inmaytide.orbit.system.domain.User;
import com.inmaytide.orbit.system.mapper.UserMapper;
import com.inmaytide.orbit.system.service.AttachmentsClient;
import com.inmaytide.orbit.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public User save(User user) {
        user.setPassword(getPasswordEncoder().encode(INITIAL_PASSWORD));
        user.setStatus(UserStatus.ENABLE);
        return super.save(user);
    }

    @Override
    public User update(User user) {
        Optional<User> original = get(user.getId());
        user.setPassword(original.orElseThrow(IllegalArgumentException::new).getPassword());
        return super.update(user);
    }

    @Override
    public void changePassword(User user) {
        User selective = new User();
        selective.setId(user.getId());
        selective.setVersion(user.getVersion());
        selective.setUpdater(user.getUpdater());
        selective.setPassword(getPasswordEncoder().encode(user.getPassword()));
        updateSelective(selective);
    }

    @Override
    public PageInfo<User> list(Params params) {
        Page<User> page = PageHelper.startPage(params.getPageNumber(), params.getPageSize());
        mapper.list(params.getContent());
        return page.toPageInfo();
    }

    @Override
    public UserMapper getMapper() {
        return mapper;
    }

    @Override
    public boolean exist(User user) {
        return getByUsername(user.getUsername())
                .filter(u -> !Objects.equals(user.getId(), u.getId()))
                .isPresent();
    }
}
