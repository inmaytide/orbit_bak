package com.inmaytide.orbit.system.domain;


import com.inmaytide.orbit.commons.database.annotation.Table;
import com.inmaytide.orbit.commons.domain.AbstractEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Table("sys_menu")
public class Menu extends AbstractEntity {

    private static final long serialVersionUID = 3253907963586618457L;

    @NotEmpty
    @Length(max = 64)
    private String code;

    @NotEmpty
    @Length(max = 64)
    private String name;

    @Pattern(regexp = "POST|GET|PUT|PATCH|DELETE", message = "Unexpected method target")
    private String method;

    @Length(max = 256)
    private String url;

    @Length(max = 512)
    private String description;

    private Long parent;

    private Integer seqOrder;

    @Length(max = 64)
    private String icon;

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Integer getSeqOrder() {
        return seqOrder;
    }

    public void setSeqOrder(Integer seqOrder) {
        this.seqOrder = seqOrder;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
