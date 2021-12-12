package server.mapper.impl;

import server.entity.User;
import server.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setRoleId(resultSet.getInt("role_id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password_hash"));
        return user;
    }
}
