package com.inmaytide.orbit.sys.service;

//import com.inmaytide.orbit.commons.query.Conditions;
//import com.inmaytide.orbit.commons.query.PagingInformation;

import com.inmaytide.orbit.sys.dao.UserRepository;
import com.inmaytide.orbit.sys.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends BasicService<UserRepository, User> {

    List<User> listByRole(Long roleId);

    List<User> listByIds(List<Long> ids);

    //Page<User> list(Conditions conditions, PagingInformation pageable);

    void remove(String ids);

    Optional<User> getByUsername(String username);

    boolean exists(List<Long> uids);
}
