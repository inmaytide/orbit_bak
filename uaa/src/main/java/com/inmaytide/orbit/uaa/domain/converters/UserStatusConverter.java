package com.inmaytide.orbit.uaa.domain.converters;

import com.inmaytide.orbit.enums.UserStatus;

import javax.persistence.AttributeConverter;

public class UserStatusConverter implements AttributeConverter<UserStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserStatus status) {
        return status.getValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(Integer value) {
        return UserStatus.valueOf(value);
    }

}
