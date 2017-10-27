package com.inmaytide.orbit.service.sys;

import com.inmaytide.orbit.auz.provider.UserProvider;
import com.inmaytide.orbit.dao.sys.UserRepository;
import com.inmaytide.orbit.domain.basic.RequestConditions;
import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.User;
import com.inmaytide.orbit.service.basic.BasicService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService extends BasicService<UserRepository, User, Long>, UserProvider {

    List<User> listByRole(Long roleId);

    List<User> listByIds(List<Long> ids);

    Page<User> list(RequestConditions conditions, RequestPageable pageable);

}
