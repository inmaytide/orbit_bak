package com.inmaytide.orbit.domain.sys;

import com.inmaytide.orbit.domain.basic.BasicEntity;
import org.springframework.data.mybatis.annotations.Entity;

/**
 * @author Moss
 * @since November 07, 2017
 */
@Entity(table = "sys_data_dictionary")
public class DataDictionary extends BasicEntity {
    private static final long serialVersionUID = -8426653633596953962L;

    private String code;

    private String name;

    private String category;

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
