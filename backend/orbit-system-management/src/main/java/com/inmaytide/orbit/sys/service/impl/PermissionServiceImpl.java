package com.inmaytide.orbit.sys.service.impl;

import com.inmaytide.orbit.enums.PermissionCategory;
import com.inmaytide.orbit.sys.domain.Permission;
import com.inmaytide.orbit.sys.dao.PermissionRepository;
import com.inmaytide.orbit.sys.dao.link.RolePermissionRepository;
import com.inmaytide.orbit.sys.service.PermissionService;
import com.inmaytide.orbit.util.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "sort");
    private static final Long MENU_ROOT_ID = -1L;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private PermissionRepository repository;

    @Override
    public Set<String> listCodesByUsername(String username) {
        return new HashSet<>(getRepository().findCodesByUsername(username));
    }

    @Override
    public List<Permission> listByRole(Long roleId) {
        return getRepository().findByRole(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_menus", allEntries = true)
    public Permission insert(Permission inst) {
        Assert.isTrue(checkCode(inst.getCode(), inst.getId()), "The permission code cannot not be repeat");
        inst.setSort(repository.getSort());
        return getRepository().save(inst);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_menus", allEntries = true)
    public Permission update(Permission inst) {
        Assert.notNull(inst.getId(), "The primary key of instance must not be null when it will be update");
        Assert.isTrue(checkCode(inst.getCode(), inst.getId()), "The permission code cannot not be repeat");

        return getRepository().findById(inst.getId())
                .map(original -> {
                    BeanUtils.copyProperties(original, inst, "parent", "createTime", "creator", "sort");
                    return repository.save(inst);
                })
                .orElseThrow(() -> new IllegalArgumentException("The instance to modify does not exist. id with [" + inst.getId() + "]"));
    }

    @Override
    @Cacheable(cacheNames = "user_menus", key = "#username + '_menus'")
    public List<Permission> listMenusByUsername(String username) {
        List<Permission> list = repository.findByUsername(username, PermissionCategory.MENU);
        return listToTreeNodes(list);
    }


    @Override
    public List<Permission> listNodes(PermissionCategory category) {
        return listToTreeNodes(repository.findByCategory(category, DEFAULT_SORT));
    }

    @Override
    public List<Permission> listNodes() {
        return listToTreeNodes(repository.findAll(DEFAULT_SORT));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_menus", allEntries = true)
    public void remove(String ids) {
        List<Long> permissionIds = split(ids);
        if (permissionIds.stream().map(this::findByParent).map(List::size).anyMatch(len -> len > 0)) {
            throw new IllegalArgumentException("Cannot remove permissions with ID [" + ids + "], maybe because its has sub permissions");
        }
        getRepository().deleteByIdIn(permissionIds);
        rolePermissionRepository.deleteByPIdIn(permissionIds);
    }

    @Override
    public Boolean checkCode(String code, Long id) {
        return StringUtils.isBlank(code) || repository.countByCodeAndIdNot(code, id == null ? MENU_ROOT_ID : id) == 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_menus", allEntries = true)
    public void move(Long id, String category) {
        Permission inst = get(id).orElseThrow(() -> new IllegalArgumentException("The permission needs to move is not exist. ID for [{}]" + id));
        Permission exchanger = getExchanger(inst, category);
        if (exchanger != null) {
            int sort = inst.getSort();
            inst.setSort(exchanger.getSort());
            exchanger.setSort(sort);
            getRepository().saveAll(List.of(inst, exchanger));
        }
    }

    @Override
    public boolean exists(List<Long> pids) {
        return !CollectionUtils.isEmpty(pids)
                && getRepository().findAllById(pids).size() == pids.size();
    }

    private String toLowerCase(String str) {
        return StringUtils.isBlank(str) ? "" : str.toLowerCase();
    }

    private Permission getExchanger(Permission current, String category) {
        ExchangerIndexProvider provider = EXCHANGER_INDEX_PROVIDERS.get(toLowerCase(category));
        Assert.notNull(provider, "The move category must be up or down");

        List<Permission> permissions = findByParent(current.getParent());
        if (permissions.size() <= 1) {
            log.error("The permission cannot to move,  because there is no same level permissions to exchange");
            return null;
        }

        return permissions.get(provider.get(permissions.indexOf(current), permissions.size()));
    }

    private List<Permission> findByParent(Long parent) {
        return repository.findByParent(parent, DEFAULT_SORT);
    }


    private List<Permission> listToTreeNodes(List<Permission> list) {
        Permission root = Permission.of(MENU_ROOT_ID);
        setChildren(root, list);
        return root.getChildren();
    }

    private void setChildren(Permission permission, List<Permission> list) {
        permission.setChildren(list.stream().filter(p -> Objects.equals(p.getParent(), permission.getId()))
                .peek(p -> setChildren(p, list))
                .collect(Collectors.toList()));
    }

    @Override
    public PermissionRepository getRepository() {
        return this.repository;
    }
}
