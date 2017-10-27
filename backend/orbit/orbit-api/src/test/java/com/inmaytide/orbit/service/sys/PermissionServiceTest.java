package com.inmaytide.orbit.service.sys;

import com.inmaytide.orbit.consts.Constants;
import com.inmaytide.orbit.consts.PermissionCategory;
import com.inmaytide.orbit.domain.sys.Permission;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionServiceTest {

    @Resource
    private PermissionService permissionService;

    @Test
    public void add() {
        Permission permission = new Permission();
        permission.setCode("workflow");
        permission.setCategory(PermissionCategory.MENU.toString());
        permission.setAction(null);
        permission.setParent(-1L);
        permission.setName("Workflow");
        permission.setCreator(9999L);
        permission = permissionService.insert(permission);
        Assert.assertNotNull(permission.getId());
    }

    @Test
    public void findAll() {
       // List<Permission> list = permissionService.list(new Permission());
        //Assert.assertEquals(list.size(), 2);
    }

}

