package com.inmaytide.orbit.sys.service.impl;


import com.inmaytide.orbit.commons.id.SnowflakeIdGenerator;
import com.inmaytide.orbit.commons.query.DefaultPageable;
import com.inmaytide.orbit.domain.sys.Role;
import com.inmaytide.orbit.domain.sys.User;
import com.inmaytide.orbit.domain.sys.link.RolePermission;
import com.inmaytide.orbit.domain.sys.link.UserRole;
import com.inmaytide.orbit.sys.dao.RoleRepository;
import com.inmaytide.orbit.sys.dao.link.RolePermissionRepository;
import com.inmaytide.orbit.sys.dao.link.UserRoleRepository;
import com.inmaytide.orbit.sys.service.PermissionService;
import com.inmaytide.orbit.sys.service.RoleService;
import com.inmaytide.orbit.sys.service.UserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Moss
 * @since September 10, 2017
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository repository;

    @Override
    public Set<String> listCodesByUsername(String username) {
        return new HashSet<>(repository.findCodesByUsername(username));
    }

    @Override
    public Page<Role> list(DefaultPageable pageModel) {
        final Pageable pageable = pageModel.transform();
        return pageModel.getKeywords()
                .map(keywords -> repository.findByCodeLikeOrNameLike(keywords, keywords, pageable))
                .orElse(repository.findAll(pageable));
    }

    @Override
    public Optional<Role> get(final Long id) {
        return repository.findById(id).map(role -> {
            role.setPermissions(permissionService.listByRole(id));
            role.setUsers(userService.listByRole(id));
            return role;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role insert(Role role) {
        role.setId(SnowflakeIdGenerator.generate());
        return repository.insert(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(String ids) {
        List<Long> roleIds = split(ids, NumberUtils::toLong, Collectors.toList());
        userRoleRepository.deleteByRIdIn(roleIds);
        rolePermissionRepository.deleteByRIdIn(roleIds);
        remove(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role update(Role role) {
        Role original = repository.findById(role.getId()).orElseThrow(IllegalArgumentException::new);
        BeanUtils.copyProperties(role, original, FINAL_FIELDS);
        return repository.update(original);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void associatePermissions(String id, String permissionIds) {
        Assert.hasText(id, "The id parameter must not be null, empty, or blank");
        Assert.hasText(permissionIds, "The permissionIds parameter must not be null, empty, or blank");
        final Long instId = NumberUtils.toLong(id);
        List<Long> pids = split(permissionIds, NumberUtils::toLong, Collectors.toList());
        Assert.isTrue(exist(instId) && permissionService.exist(pids), "The role and permissions all must exist");
        rolePermissionRepository.deleteByRIdIn(List.of(instId));
        pids.stream()
                .map(permissionId -> new RolePermission(SnowflakeIdGenerator.generate(), instId, permissionId))
                .forEach(rolePermissionRepository::insert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<User> associateUsers(String id, String userIds) {
        Assert.hasText(id, "The id parameter must not be null, empty, or blank");
        Assert.hasText(userIds, "The permissionIds parameter must not be null, empty, or blank");
        final Long instId = NumberUtils.toLong(id);
        List<Long> uids = split(userIds, NumberUtils::toLong, Collectors.toList());
        Assert.isTrue(exist(instId) && userService.exist(uids), "The role and users all must exist");
        uids.stream()
                .map(uid -> new UserRole(SnowflakeIdGenerator.generate(), uid, instId))
                .forEach(userRoleRepository::insert);
        return userService.listByIds(uids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAssociatedUsers(String id, String userIds) {
        final Long roleId = NumberUtils.toLong(id);
        List<Long> ids = split(userIds, NumberUtils::toLong, Collectors.toList());
        userRoleRepository.deleteByRIdAndUIdIn(roleId, ids);
    }

    @Override
    public boolean exist(Long id) {
        return id != 0 && repository.existsById(id);
    }

    @Override
    public Boolean checkCode(String code, Long id) {
        return repository.countByCodeAndIdNot(code, id) == 0;
    }

    @Override
    public RoleRepository getRepository() {
        return this.repository;
    }
}
