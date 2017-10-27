package com.inmaytide.orbit.service.sys.impl;

import com.inmaytide.orbit.auz.cache.annotation.ClearAuthorizationCache;
import com.inmaytide.orbit.dao.sys.RolePermissionRepository;
import com.inmaytide.orbit.dao.sys.RoleRepository;
import com.inmaytide.orbit.dao.sys.UserRoleRepository;
import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.Role;
import com.inmaytide.orbit.domain.sys.User;
import com.inmaytide.orbit.domain.sys.link.RolePermission;
import com.inmaytide.orbit.domain.sys.link.UserRole;
import com.inmaytide.orbit.log.LogAnnotation;
import com.inmaytide.orbit.pk.IdGenerator;
import com.inmaytide.orbit.service.sys.PermissionService;
import com.inmaytide.orbit.service.sys.RoleService;
import com.inmaytide.orbit.service.sys.UserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
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

    @Resource
    private PermissionService permissionService;

    @Resource
    private UserService userService;

    @Resource
    private RolePermissionRepository rolePermissionRepository;

    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private RoleRepository repository;

    @Override
    public Set<String> listCodesByUsername(String username) {
        return new HashSet<>(repository.findCodesByUsername(username));
    }

    @Override
    public Page<Role> list(RequestPageable pageModel) {
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
    @LogAnnotation("新增角色")
    @Transactional(rollbackFor = Exception.class)
    public Role insert(Role role) {
        role.setId(IdGenerator.nextId());
        return repository.insert(role);
    }

    @Override
    @LogAnnotation("删除角色")
    @Transactional(rollbackFor = Exception.class)
    public void remove(String ids) {
        List<Long> roleIds = split(ids, NumberUtils::toLong, Collectors.toList());
        userRoleRepository.deleteByRIdIn(roleIds);
        rolePermissionRepository.deleteByRIdIn(roleIds);
        remove(roleIds);
    }

    @Override
    @LogAnnotation("修改角色基本信息")
    @Transactional(rollbackFor = Exception.class)
    public Role update(Role role) {
        Role original = repository.findById(role.getId()).orElseThrow(IllegalArgumentException::new);
        BeanUtils.copyProperties(role, original, FINAL_FIELDS);
        return repository.update(original);
    }

    @Override
    @LogAnnotation("修改角色权限")
    @Transactional(rollbackFor = Exception.class)
    @ClearAuthorizationCache
    public void associatePermissions(String id, String permissionIds) {
        Assert.hasText(id, "The id parameter must not be null, empty, or blank");
        Assert.hasText(permissionIds, "The permissionIds parameter must not be null, empty, or blank");
        final Long instId = NumberUtils.toLong(id);
        List<Long> pids = split(permissionIds, NumberUtils::toLong, Collectors.toList());
        Assert.isTrue(exist(instId) && permissionService.exist(pids), "The role and permissions all must exist");
        rolePermissionRepository.deleteByRIdIn(List.of(instId));
        pids.stream()
                .map(permissionId -> new RolePermission(IdGenerator.nextId(), instId, permissionId))
                .forEach(rolePermissionRepository::insert);
    }

    @Override
    @LogAnnotation("角色关联用户")
    @Transactional(rollbackFor = Exception.class)
    public List<User> associateUsers(String id, String userIds) {
        Assert.hasText(id, "The id parameter must not be null, empty, or blank");
        Assert.hasText(userIds, "The permissionIds parameter must not be null, empty, or blank");
        final Long instId = NumberUtils.toLong(id);
        List<Long> uids = split(userIds, NumberUtils::toLong, Collectors.toList());
        Assert.isTrue(exist(instId) && userService.exist(uids), "The role and users all must exist");
        uids.stream()
                .map(uid -> new UserRole(IdGenerator.nextId(), uid, instId))
                .forEach(userRoleRepository::insert);
        return userService.listByIds(uids);
    }

    @Override
    @LogAnnotation("移除关联用户")
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
