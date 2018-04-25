package com.inmaytide.orbit.sys.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.sys.enums.PermissionCategory;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sys_permission")
@EntityListeners(AuditingEntityListener.class)
public class Permission extends AbstractEntity {

    private static final long serialVersionUID = -5401749095217234229L;

    @NotBlank
    @Length(max = 32)
    private String code;

    @NotBlank
    @Length(max = 64)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private HttpMethod method;

    @Length(max = 256)
    private String action;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long icon;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PermissionCategory category;

    @Length(max = 256)
    private String description;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parent;

    private Integer sort;

    @Column(name = "id_path")
    private String idPath;

    @Transient
    private List<Permission> children;

    public static Permission of(Long id) {
        Permission permission = new Permission();
        permission.setId(id);
        return permission;
    }

    public static Permission of(Long id, Integer sort, Integer version) {
        Permission permission = of(id);
        permission.setSort(sort);
        permission.setVersion(version);
        return permission;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getIcon() {
        return icon;
    }

    public void setIcon(Long icon) {
        this.icon = icon;
    }

    public PermissionCategory getCategory() {
        return category;
    }

    public void setCategory(PermissionCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setMethod(String method) {
        this.method = HttpMethod.valueOf(method);
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }

    @Override
    public boolean equals(Object obj) {
        if (!Permission.class.isInstance(obj)) {
            return false;
        }
        Permission other = (Permission) obj;
        return Objects.equals(other.getId(), this.getId());
    }
}
