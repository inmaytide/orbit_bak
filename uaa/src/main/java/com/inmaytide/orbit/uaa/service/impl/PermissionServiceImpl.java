package com.inmaytide.orbit.uaa.service.impl;

import com.inmaytide.orbit.uaa.repository.PermissionRepository;
import com.inmaytide.orbit.uaa.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luomiao
 * @since 2019/09/11
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository repository;

    @Override
    @Cacheable(value = "user_permissions", key = "#id")
    public List<String> getCodesByUser(Long id) {
        return repository.getCodesByUser(id);
    }
}
