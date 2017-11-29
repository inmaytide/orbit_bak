package com.inmaytide.orbit.sys.service.impl;

import com.inmaytide.orbit.commons.query.Conditions;
import com.inmaytide.orbit.commons.query.PagingInformation;
import com.inmaytide.orbit.sys.dao.UserRepository;
import com.inmaytide.orbit.sys.dao.link.UserOrganizationRepository;
import com.inmaytide.orbit.sys.dao.specification.UserSpecification;
import com.inmaytide.orbit.sys.domain.User;
import com.inmaytide.orbit.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserOrganizationRepository userOrganizationRepository;

    @Override
    public List<User> listByRole(Long roleId) {
        return null;
    }

    @Override
    public List<User> listByIds(List<Long> ids) {
        return null;
    }

    @Override
    public Page<User> list(Conditions conditions, PagingInformation pageable) {
        return repository.findAll(new UserSpecification(conditions, userOrganizationRepository), pageable.transform());
    }

    @Override
    public void remove(String ids) {

    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(getRepository().findByUsername(username));
    }

    @Override
    public UserRepository getRepository() {
        return repository;
    }
}
