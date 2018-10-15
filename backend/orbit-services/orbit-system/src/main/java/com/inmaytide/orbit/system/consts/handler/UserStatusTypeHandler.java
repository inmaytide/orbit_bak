package com.inmaytide.orbit.system.consts.handler;

import com.inmaytide.orbit.system.consts.UserStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({UserStatus.class})
public class UserStatusTypeHandler extends BaseTypeHandler<UserStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UserStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public UserStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return UserStatus.of(rs.getInt(columnName));
    }

    @Override
    public UserStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return UserStatus.of(rs.getInt(columnIndex));
    }

    @Override
    public UserStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return UserStatus.of(cs.getInt(columnIndex));
    }
}
