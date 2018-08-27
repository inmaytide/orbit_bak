package com.inmaytide.orbit.system.domain;

import com.inmaytide.orbit.commons.database.annotation.OrderBy;
import com.inmaytide.orbit.commons.database.annotation.Table;
import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.system.consts.OrganizationCategory;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Table("sys_organization")
public class Organization extends AbstractEntity {

    @NotEmpty
    @Length(max = 64)
    @OrderBy
    private String code;

    @NotEmpty
    @Length(max = 64)
    private String name;

    @Length(max = 512)
    private String remark;

    @Length(max = 16)
    private String telephone;

    private Long parent;

    private OrganizationCategory category;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public OrganizationCategory getCategory() {
        return category;
    }

    public void setCategory(OrganizationCategory category) {
        this.category = category;
    }
}
