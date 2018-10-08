package com.inmaytide.orbit.system.domain;

import com.inmaytide.orbit.commons.database.annotation.Table;
import com.inmaytide.orbit.commons.domain.AbstractEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Function extends AbstractEntity {

    private static final long serialVersionUID = 5089182578612526382L;

    private Long menuId;

    @NotEmpty
    @Length(max = 64)
    private String code;

    @NotEmpty
    @Length(max = 64)
    private String name;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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

}
