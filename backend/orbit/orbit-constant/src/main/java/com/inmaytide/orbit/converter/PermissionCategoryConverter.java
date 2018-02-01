package com.inmaytide.orbit.converter;

import com.inmaytide.orbit.enums.PermissionCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PermissionCategoryConverter implements AttributeConverter<PermissionCategory, Long> {
    @Override
    public Long convertToDatabaseColumn(PermissionCategory attribute) {
        return attribute.getCode();
    }

    @Override
    public PermissionCategory convertToEntityAttribute(Long dbData) {
        return PermissionCategory.valueOf(dbData);
    }
}
