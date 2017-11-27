package com.inmaytide.orbit.sys.service;

import com.inmaytide.orbit.commons.query.PagingInformation;
import com.inmaytide.orbit.commons.service.BasicService;
import com.inmaytide.orbit.sys.dao.RoleRepository;
import com.inmaytide.orbit.sys.domain.Role;
import com.inmaytide.orbit.sys.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface RoleService extends BasicService<RoleRepository, Role, Long> {

    String[] FINAL_FIELDS = new String[]{"id", "createTime", "creator"};

    Page<Role> list(PagingInformation pageable);

    void remove(String ids);

    Boolean checkCode(String code, Long id);

    void associatePermissions(String id, String permissionIds);

    List<User> associateUsers(String id, String userIds);

    void removeAssociatedUsers(String id, String userIds);

    Set<String> listCodesByUsername(String username);
}
