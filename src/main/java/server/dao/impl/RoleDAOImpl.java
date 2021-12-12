package server.dao.impl;

import server.dao.AbstractDAO;
import server.dao.daos.RoleDAO;
import server.entity.Role;
import server.exception.DAOException;
import server.mapper.RowMapper;
import server.mapper.RowMapperFactory;

import java.sql.Connection;
import java.util.Optional;

public class RoleDAOImpl extends AbstractDAO<Role> implements RoleDAO {
    private static final String TABLE_NAME = "roles";

    private static final String INSERT_ROLE = "INSERT INTO " + TABLE_NAME + " (Name) VALUES (?)";

    private static final String SELECT_ROLE_WHERE_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE Name=?";

    public RoleDAOImpl(Connection connection) {
        super(connection, RowMapperFactory.getInstance().getRoleRowMapper(), TABLE_NAME);
    }

    @Override
    public int add(Role item) throws DAOException {
        return executeInsert(INSERT_ROLE, item.getName());
    }

    @Override
    public Optional<Role> getByName(String name) throws DAOException {
        return executeForSingleResult(SELECT_ROLE_WHERE_NAME, name);
    }
}
