package com.inmaytide.orbit.sys.service;

import com.inmaytide.orbit.commons.service.BasicService;
import com.inmaytide.orbit.sys.dao.PermissionRepository;
import com.inmaytide.orbit.sys.domain.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService extends BasicService<PermissionRepository, Permission, Long> {

    String[] FINAL_FIELDS = new String[]{"id", "parent", "create_time", "creator", "sort"};

    List<Permission> listByRole(Long roleId);

    /**
     * Get permissions by category and transform to tree nodes.
     *
     * @param category MENU or BUTTON
     * @return Tree structure permissions
     * @see PermissionCategory
     */
    List<Permission> listNodes(String category);

    /**
     * Get all permissions  and transform to tree nodes.
     *
     * @return Tree structure permissions
     */
    List<Permission> listNodes();

    Boolean checkCode(String code, Long id);

    void move(Long id, String category);

    void remove(String ids);

    Set<String> listCodesByUsername(String username);

    List<Permission> listMenusByUsername(String username);
}
