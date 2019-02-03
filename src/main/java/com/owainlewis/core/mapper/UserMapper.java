package com.owainlewis.core.mapper;

import com.owainlewis.core.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .first(resultSet.getString("first"))
                .last((resultSet.getString("last")))
                .email(resultSet.getString("email"))
                .build();
    }
}
