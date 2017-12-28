package com.inmaytide.orbit.sys.service;

import com.inmaytide.orbit.commons.service.BasicService;
import com.inmaytide.orbit.sys.dao.PermissionRepository;
import com.inmaytide.orbit.sys.domain.Permission;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PermissionService extends BasicService<PermissionRepository, Permission, Long> {

    List<Permission> listByRole(Long roleId);

    /**
     * Get permissions by category and transform to tree nodes.
     *
     * @param category MENU or BUTTON
     * @return Tree structure permissions
     */
    List<Permission> listNodes(Long category);

    /**
     * Get all permissions and transform to tree nodes.
     *
     * @return Tree structure permissions
     */
    List<Permission> listNodes();

    Boolean checkCode(String code, Long id);

    void move(Long id, String category);

    void remove(String ids);

    Set<String> listCodesByUsername(String username);

    List<Permission> listMenusByUsername(String username);

    @FunctionalInterface
    interface ExchangerIndexProvider {
        int get(int index, int len);
    }

    Map<String, ExchangerIndexProvider> EXCHANGER_INDEX_PROVIDERS = Map.of("up", (index, len) -> index == 0 ? len - 1 : index - 1,
            "down", (index, len) -> index == len - 1 ? 0 : index + 1);

}
