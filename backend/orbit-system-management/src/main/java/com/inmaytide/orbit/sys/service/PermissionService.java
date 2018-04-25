package com.inmaytide.orbit.sys.service;

import com.inmaytide.orbit.sys.dao.PermissionRepository;
import com.inmaytide.orbit.sys.domain.Permission;
import com.inmaytide.orbit.sys.enums.PermissionCategory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PermissionService extends BasicService<PermissionRepository, Permission> {

    Permission insert(Permission inst);

    Permission update(Permission inst);

    List<Permission> listByRole(Long roleId);

    /**
     * Get permissions by category and transform to tree nodes.
     * @param category {@link PermissionCategory}
     * @return tree structure permissions
     * @see com.inmaytide.orbit.sys.enums.PermissionCategory
     */
    List<Permission> listNodes(PermissionCategory category);

    /**
     * Get all permissions and transform to tree nodes.
     * @return tree structure permissions
     */
    List<Permission> listNodes();

    /**
     * Check if a permission code already exists
     * @param code The permission code to check
     * @param exculde exclude permission
     * @return if exists return false else return true
     */
    Boolean checkCode(String code, Long excluded);

    /**
     * Exchange the sort value with the previous or next permission
     *
     * @param Primary  key that needs to move permissions
     * @param category up or down
     */
    void move(Long id, String category);

    void remove(String ids);

    Set<String> listCodesByUsername(String username);

    List<Permission> listMenusByUsername(String username);

    boolean exists(List<Long> pids);

    @FunctionalInterface
    interface ExchangerIndexProvider {
        int get(int index, int len);
    }

    Map<String, ExchangerIndexProvider> EXCHANGER_INDEX_PROVIDERS = Map.of(
            "up", (index, len) -> index == 0 ? len - 1 : index - 1,
            "down", (index, len) -> index == len - 1 ? 0 : index + 1
    );

}
