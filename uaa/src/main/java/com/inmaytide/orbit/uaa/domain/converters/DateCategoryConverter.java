package com.inmaytide.orbit.uaa.domain.converters;

import com.inmaytide.orbit.enums.DataCategory;

import javax.persistence.AttributeConverter;

public class DateCategoryConverter implements AttributeConverter<DataCategory, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DataCategory status) {
        return status.getValue();
    }

    @Override
    public DataCategory convertToEntityAttribute(Integer value) {
        return DataCategory.valueOf(value);
    }

}
