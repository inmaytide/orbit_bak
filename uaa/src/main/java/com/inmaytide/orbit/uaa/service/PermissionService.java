package com.inmaytide.orbit.uaa.service;

import java.util.List;

/**
 * @author luomiao
 * @since 2019/09/11
 */
public interface PermissionService {

    List<String> getCodesByUser(Long id);

}
