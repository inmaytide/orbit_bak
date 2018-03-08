package com.inmaytide.orbit.dictionary.domain;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.dictionary.handler.DataDictionaryHandler;
import com.inmaytide.orbit.enums.PermissionCategory;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpMethod;

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

//    public static DataDictionaryBuilder withPermissionCategory(PermissionCategory category) {
//        return new DataDictionaryBuilder().code(category.name())
//                .text(category.getText())
//                .category(DataDictionaryHandler.KEY_PERMISSION_CATEGORY);
//    }
//
//    public static DataDictionaryBuilder withHttpMethod(HttpMethod method) {
//        return new DataDictionaryBuilder().code(method.name())
//                .text(method.name())
//                .category(DataDictionaryHandler.KEY_HTTP_METHOD);
//    }


//    public static class DataDictionaryBuilder {
//        private String category;
//        private String text;
//        private String code;
//
//        private DataDictionaryBuilder() {
//
//        }
//
//        public DataDictionaryBuilder category(String category) {
//            this.category = category;
//            return this;
//        }
//
//        public DataDictionaryBuilder text(String text) {
//            this.text = text;
//            return this;
//        }
//
//        public DataDictionaryBuilder code(String code) {
//            this.code = code;
//            return this;
//        }
//
//        public DataDictionary build() {
//            DataDictionary inst = new DataDictionary();
//            inst.setCategory(category);
//            inst.setText(text);
//            inst.setCode(code);
//            return inst;
//        }
//    }
}
