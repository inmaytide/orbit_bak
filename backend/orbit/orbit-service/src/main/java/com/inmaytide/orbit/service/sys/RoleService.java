package com.inmaytide.orbit.service.sys;

import com.inmaytide.orbit.auz.provider.RoleProvider;
import com.inmaytide.orbit.dao.sys.RoleRepository;
import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.Role;
import com.inmaytide.orbit.domain.sys.User;
import com.inmaytide.orbit.service.basic.BasicService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService extends BasicService<RoleRepository, Role, Long>, RoleProvider {

    String[] FINAL_FIELDS = new String[]{"id", "createTime", "creator"};

    Page<Role> list(RequestPageable pageable);

    void remove(String ids);

    Boolean checkCode(String code, Long id);

    void associatePermissions(String id, String permissionIds);

    List<User> associateUsers(String id, String userIds);

    void removeAssociatedUsers(String id, String userIds);
}
