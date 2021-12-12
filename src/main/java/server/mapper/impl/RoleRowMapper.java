package server.mapper.impl;

import server.entity.Role;
import server.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role map(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getInt("Id"));
        role.setName(resultSet.getString("Name"));
        return role;
    }
}
