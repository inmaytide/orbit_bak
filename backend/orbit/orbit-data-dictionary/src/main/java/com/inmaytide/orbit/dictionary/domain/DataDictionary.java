package com.inmaytide.orbit.dictionary.domain;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Moss
 * @since November 28, 2017
 */
@Entity
@Table(name = "sys_data_dictionary")
@EntityListeners(value = AuditingEntityListener.class)
public class DataDictionary extends AbstractEntity {

    private static final long serialVersionUID = -8124865319194501084L;

    @NotBlank
    @Length(max = 64)
    private String text;

    @NotBlank
    @Length(max = 64)
    private String code;

    @NotBlank
    @Length(max = 128)
    private String category;

    private Integer sort;

    @Column(name = "is_created_by_system")
    private Integer isCreatedBySystem;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsCreatedBySystem() {
        return isCreatedBySystem;
    }

    public void setIsCreatedBySystem(Integer isCreatedBySystem) {
        this.isCreatedBySystem = isCreatedBySystem;
    }
}
