package com.inmaytide.orbit.sys.domain;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.sys.enums.OrganizationCategory;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sys_organization")
public class Organization extends AbstractEntity {

    private static final long serialVersionUID = 7250670050182339430L;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrganizationCategory category;

    private Long parent;

    private String address;

    private String description;

    @Column(name = "is_removed")
    private Integer isRemoved;

    private Integer sort;

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

    public OrganizationCategory getCategory() {
        return category;
    }

    public void setCategory(OrganizationCategory category) {
        this.category = category;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Integer isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
