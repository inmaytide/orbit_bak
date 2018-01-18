package com.inmaytide.orbit.sys.domain;

import com.inmaytide.orbit.commons.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
    private Long category;

    private Long parent;

    private String address;

    private String description;

    @Column(name = "is_removed")
    private int isRemoved;

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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
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

    public int getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(int isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
