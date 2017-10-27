package com.inmaytide.orbit.auz.provider;

import com.inmaytide.orbit.domain.sys.Permission;

import java.util.List;
import java.util.Set;

/**
 * @author Moss
 * @since October 04, 2017
 */
public interface PermissionProvider {

    Set<String> listCodesByUsername(String username);

    List<Permission> listMenusByUsername(String username);

}
