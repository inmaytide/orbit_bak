package com.inmaytide.orbit.system.consts.handler;

import com.inmaytide.orbit.system.consts.OrganizationCategory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({OrganizationCategory.class})
public class OrganizationCategoryTypeHandler extends BaseTypeHandler<OrganizationCategory> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OrganizationCategory parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public OrganizationCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return OrganizationCategory.of(rs.getInt(columnName));
    }

    @Override
    public OrganizationCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return OrganizationCategory.of(rs.getInt(columnIndex));
    }

    @Override
    public OrganizationCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return OrganizationCategory.of(cs.getInt(columnIndex));
    }
}
