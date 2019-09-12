package com.inmaytide.orbit.uaa.service.impl;

import com.inmaytide.orbit.uaa.domain.WebMenu;
import com.inmaytide.orbit.uaa.repository.WebMenuRepository;
import com.inmaytide.orbit.uaa.service.WebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author luomiao
 * @since 2019/09/12
 */
@Service
public class WebMenuServiceImpl implements WebMenuService {

    @Autowired
    private WebMenuRepository repository;

    @Override
    public List<WebMenu> getByUser(Long userId) {
        List<WebMenu> menus = repository.getByUser(userId);
        return tree(menus, 0L, 1);
    }

    private List<WebMenu> tree(List<WebMenu> all, Long parent, int level) {
        return all.stream().filter(m -> Objects.equals(m.getParent(), parent))
                .peek(m -> {
                    m.setLevel(level);
                    m.setExpand(false);
                    m.setChildren(tree(all, m.getId(), level + 1));
                }).collect(Collectors.toList());
    }
}
