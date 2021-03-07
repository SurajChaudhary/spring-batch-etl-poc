package com.devtalk.batch.etl.config;

import com.devtalk.batch.etl.model.Users;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements org.springframework.jdbc.core.RowMapper<Users> {
    @Override
    public Users mapRow(ResultSet resultSet, int rowId) throws SQLException {
        return new Users(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("role"),
                resultSet.getInt("salary"));
    }
}
