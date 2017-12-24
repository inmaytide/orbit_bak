package com.inmaytide.orbit.sys.service.impl;

import com.inmaytide.orbit.sys.domain.Permission;
import com.inmaytide.orbit.sys.dao.PermissionRepository;
import com.inmaytide.orbit.sys.dao.link.RolePermissionRepository;
import com.inmaytide.orbit.sys.service.PermissionService;
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

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {

    public static final Long MENU_ROOT_ID = -1L;
    private static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "sort");
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
        inst.setSort(repository.getSort());
        return getRepository().save(inst);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_menus", allEntries = true)
    public Permission update(Permission inst) {
        Permission original = getRepository().findById(inst.getId()).orElseThrow(() -> new IllegalArgumentException("The instance to modify does not exist. id with [" + inst.getId() + "]"));
        inst.setParent(original.getParent());
        inst.setCreateTime(original.getCreateTime());
        inst.setCreator(original.getCreator());
        inst.setSort(original.getSort());
        return repository.save(inst);
    }

    @Override
    @Cacheable(cacheNames = "user_menus", key = "#username + '_menus'")
    public List<Permission> listMenusByUsername(String username) {
        List<Permission> list = repository.findByUsername(username, 377564822935437312L);
        return listToTreeNodes(list);
    }


    @Override
    public List<Permission> listNodes(Long category) {
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
        List<Long> permissionIds = split(ids, Long::valueOf, Collectors.toList());
        permissionIds
                .stream()
                .map(this::findByParent)
                .forEach(permissions -> {
                    if (permissions.size() > 0) {
                        throw new IllegalArgumentException("Cannot remove permissions with ID [" + ids + "], maybe because its has sub permissions");
                    }
                });

        remove(permissionIds);
        rolePermissionRepository.deleteByPIdIn(permissionIds);
    }

    @Override
    public Boolean checkCode(String code, Long id) {
        id = id == null ? -1 : id;
        return repository.countByCodeAndIdNot(code, id) == 0;
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

    private String toLowerCase(String str) {
        return StringUtils.isBlank(str) ? "" : str.toLowerCase();
    }

    private Permission getExchanger(Permission current, String category) {
        ExchangerIndexProvider provider = EXCHANGER_INDEX_PROVIDERS.get(toLowerCase(category));
        Assert.isNull(provider, "The move category must be up or down");

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
