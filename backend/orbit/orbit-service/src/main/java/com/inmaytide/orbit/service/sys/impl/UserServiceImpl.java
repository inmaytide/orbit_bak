package com.inmaytide.orbit.service.sys.impl;

import com.inmaytide.orbit.dao.sys.UserRepository;
import com.inmaytide.orbit.domain.basic.RequestConditions;
import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.User;
import com.inmaytide.orbit.service.sys.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    public UserRepository userRepository;

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(getRepository().findByUsername(username));
    }

    @Override
    public List<User> listByIds(List<Long> ids) {
        List<User> users = new ArrayList<>();
        getRepository().findAllById(ids).forEach(users::add);
        return users;
    }

    @Override
    public List<User> listByRole(Long roleId) {
        return getRepository().findByRole(roleId);
    }

    @Override
    public Page<User> list(RequestConditions conditions, RequestPageable pageModel) {
        Pageable pageable = pageModel.transform();
        pagingInformation(conditions, pageable);
        List<User> users = getRepository().findList(conditions.getConditions());
        Integer count = getRepository().findCount(conditions.getConditions());
        Page<User> page = new PageImpl<>(users, pageable, count);
        log.debug("Get users with conditions [{}]", conditions.toString());
        return page;
    }

    @Override
    public UserRepository getRepository() {
        return this.userRepository;
    }
}
