package com.inmaytide.orbit.service.sys.impl;

import com.inmaytide.orbit.consts.Constants;
import com.inmaytide.orbit.consts.PermissionCategory;
import com.inmaytide.orbit.dao.sys.PermissionRepository;
import com.inmaytide.orbit.dao.sys.link.RolePermissionRepository;
import com.inmaytide.orbit.domain.sys.Permission;
import com.inmaytide.orbit.exception.VersionMatchedException;
import com.inmaytide.orbit.log.LogAnnotation;
import com.inmaytide.orbit.pk.IdGenerator;
import com.inmaytide.orbit.service.sys.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mybatis.repository.support.MybatisNoHintException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "sort");

    @Resource
    private RolePermissionRepository rolePermissionRepository;

    @Resource
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
    @LogAnnotation("添加菜单")
    @CacheEvict(value = "user_menus", allEntries = true)
    public Permission insert(Permission inst) {
        inst.setId(IdGenerator.nextId());
        inst.setSort(repository.getSort());
        return getRepository().insert(inst);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogAnnotation("修改菜单")
    @CacheEvict(value = "user_menus", allEntries = true)
    public Permission update(Permission inst) {
        Permission origin = getRepository().findById(inst.getId()).orElseThrow(IllegalArgumentException::new);
        BeanUtils.copyProperties(inst, origin, FINAL_FIELDS);
        try {
            return repository.update(origin);
        } catch (MybatisNoHintException e) {
            throw new VersionMatchedException(e);
        }
    }

    @Override
    @Cacheable(cacheNames = "user_menus", key = "#username + '_menus'")
    public List<Permission> listMenusByUsername(String username) {
        List<Permission> list = repository.findByUsername(username, PermissionCategory.MENU.getCode());
        return listToTreeNodes(list);
    }


    @Override
    public List<Permission> listNodes(String category) {
        return listToTreeNodes(repository.findByCategory(category, DEFAULT_SORT));
    }

    @Override
    public List<Permission> listNodes() {
        return listToTreeNodes(repository.findAll(DEFAULT_SORT));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogAnnotation("删除菜单")
    @CacheEvict(value = "user_menus", allEntries = true)
    public void remove(String ids) {
        List<Long> permissionIds = split(ids, Long::valueOf, Collectors.toList());
        remove(permissionIds);
        rolePermissionRepository.deleteByPIdIn(permissionIds);
    }

    @Override
    public Boolean checkCode(String code, Long id) {
        return repository.countByCodeAndIdNot(code, id) == 0;
    }

    @Override
    @LogAnnotation("修改菜单排序")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_menus", allEntries = true)
    public void move(Long id, String category) {
        Permission inst = get(id).orElseThrow(() -> new IllegalArgumentException("The permission needs to move is not exist. ID for [{}]" + id));
        List<Permission> permissions = findByParent(inst.getParent());
        if (permissions.size() <= 1) {
            log.error("The permission cannot to move,  because there is no same level permissions to exchange");
            return;
        }
        int index = permissions.indexOf(inst);
        Permission other = getOther(index, permissions, category);
        modifySort(inst.getId(), other.getSort(), inst.getVersion());
        modifySort(other.getId(), inst.getSort(), other.getVersion());
    }

    private Permission getOther(int index, List<Permission> permissions, String category) {
        int otherIndex, len = permissions.size();
        if ("up".equals(category)) {
            otherIndex = index == 0 ? len - 1 : index - 1;
        } else if ("down".equals(category)) {
            otherIndex = index == len - 1 ? 0 : index + 1;
        } else {
            throw new IllegalArgumentException("The move category must be up or down");
        }
        return permissions.get(otherIndex);
    }

    private List<Permission> findByParent(Long parent) {
        return repository.findByParent(parent, DEFAULT_SORT);
    }

    private void modifySort(Long id, Integer sort, int version) {
        try {
            getRepository().updateIgnoreNull(new Permission(id, sort, version));
        } catch (MybatisNoHintException e) {
            throw new VersionMatchedException(e);
        }
    }

    private List<Permission> listToTreeNodes(List<Permission> list) {
        Permission root = Permission.of(Constants.MENU_ROOT_ID);
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
