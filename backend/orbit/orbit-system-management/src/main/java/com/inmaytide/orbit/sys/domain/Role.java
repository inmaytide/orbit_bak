package com.inmaytide.orbit.sys.domain;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.domain.basis.AbstractEntity;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.Entity;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(table = "sys_role")
public class Role extends AbstractEntity {

    private static final long serialVersionUID = -8038307119098134671L;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String description;

    @Transient
    private List<Permission> permissions;

    @Transient
    private List<User> users;

    public Role() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
