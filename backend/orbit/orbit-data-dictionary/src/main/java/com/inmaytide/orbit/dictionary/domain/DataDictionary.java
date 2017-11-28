package com.inmaytide.orbit.dictionary.domain;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Moss
 * @since November 28, 2017
 */
@Entity
@Table(name = "sys_data_dictionary")
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
}
