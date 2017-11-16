package com.inmaytide.orbit.sys.service;

import com.inmaytide.orbit.commons.query.Conditions;
import com.inmaytide.orbit.commons.query.DefaultPageable;
import com.inmaytide.orbit.commons.service.BasicService;
import com.inmaytide.orbit.domain.sys.User;
import com.inmaytide.orbit.sys.dao.UserRepository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService extends BasicService<UserRepository, User, Long> {

    List<User> listByRole(Long roleId);

    List<User> listByIds(List<Long> ids);

    Page<User> list(Conditions conditions, DefaultPageable pageable);

    void remove(String ids);

    Optional<User> getByUsername(String username);
}
