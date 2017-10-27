package com.inmaytide.orbit.domain.sys;

import com.inmaytide.orbit.domain.basic.BasicEntity;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Entity;

@Entity(name = "sys_organization")
public class Organization extends BasicEntity {

    private static final long serialVersionUID = 7250670050182339430L;

    private String code;

    private String name;

    private Long category;

    private Long parent;

    private String address;

    private String description;

    @Column(name = "is_removed")
    private int isRemoved;

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
}
