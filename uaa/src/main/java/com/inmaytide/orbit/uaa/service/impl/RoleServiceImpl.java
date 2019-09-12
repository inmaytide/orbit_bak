package com.inmaytide.orbit.uaa.service.impl;

import com.inmaytide.orbit.uaa.domain.Role;
import com.inmaytide.orbit.uaa.repository.RoleRepository;
import com.inmaytide.orbit.uaa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author luomiao
 * @since 2019/09/11
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    @Cacheable(value = "user_roles", key = "#id")
    public List<String> getCodesByUser(Long id) {
        return repository.getCodesByUser(id);
    }

    @Override
    public Optional<Role> get(Long id) {
        return repository.findById(id);
    }

    @Override
    @CacheEvict(value = {"user_roles", "user_permissions"}, allEntries = true)
    public Optional<Role> create(Role inst) {
        repository.save(inst);
        associateUsers(inst);
        associatePermissions(inst);
        return get(inst.getId());
    }

    @Override
    @CacheEvict(value = {"user_roles", "user_permissions"}, allEntries = true)
    public Optional<Role> modify(Role inst) {
        repository.save(inst);
        associateUsers(inst);
        associatePermissions(inst);
        return get(inst.getId());
    }

    private void associateUsers(Role role) {

    }

    private void associatePermissions(Role role) {

    }
}
