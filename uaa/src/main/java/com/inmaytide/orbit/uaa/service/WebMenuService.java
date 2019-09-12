package com.inmaytide.orbit.uaa.service;

import com.inmaytide.orbit.uaa.domain.WebMenu;

import java.util.List;

/**
 * @author luomiao
 * @since 2019/09/12
 */
public interface WebMenuService {

    List<WebMenu> getByUser(Long userId);
}
