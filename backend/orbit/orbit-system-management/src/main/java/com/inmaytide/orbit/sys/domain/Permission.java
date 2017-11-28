package com.inmaytide.orbit.sys.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.inmaytide.orbit.commons.domain.AbstractEntity;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @NotBlank
    @Pattern(regexp = "ALL|POST|GET|PUT|PATCH|DELETE")
    private String method;

    @Length(max = 256)
    private String action;

    @Length(max = 256)
    private String icon;

    @NotNull
    private String category;

    @Length(max = 256)
    private String description;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parent;

    private Integer sort;

    @Transient
    private List<Permission> children;

    public Permission() {
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !Permission.class.isInstance(obj)) {
            return false;
        }
        Permission other = (Permission) obj;
        return Objects.equals(other.getId(), this.getId());
    }
}
