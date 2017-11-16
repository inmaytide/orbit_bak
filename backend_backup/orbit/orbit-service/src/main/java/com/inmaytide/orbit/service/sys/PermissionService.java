package com.inmaytide.orbit.service.sys;

import com.inmaytide.orbit.auz.provider.PermissionProvider;
import com.inmaytide.orbit.consts.PermissionCategory;
import com.inmaytide.orbit.dao.sys.PermissionRepository;
import com.inmaytide.orbit.domain.sys.Permission;
import com.inmaytide.orbit.service.basic.BasicService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface PermissionService extends BasicService<PermissionRepository, Permission, Long>, PermissionProvider {

    String[] FINAL_FIELDS = new String[]{"id", "parent", "create_time", "creator", "sort"};

    List<Permission> listByRole(Long roleId);

    /**
     * Get permissions by category and transform to tree nodes.
     * @param category MENU or BUTTON
     * @return Tree structure permissions
     * @see PermissionCategory
     */
    List<Permission> listNodes(String category);

    /**
     * Get all permissions  and transform to tree nodes.
     * @return Tree structure permissions
     */
    List<Permission> listNodes();

    Boolean checkCode(String code, Long id);

    void move(Long id, String category);

    void remove(String ids);
}
