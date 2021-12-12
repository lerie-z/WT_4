package server.dao.impl;

import org.apache.commons.codec.digest.DigestUtils;
import server.dao.AbstractDAO;
import server.dao.daos.UserDAO;
import server.entity.User;
import server.exception.DAOException;
import server.mapper.RowMapper;
import server.mapper.RowMapperFactory;

import java.sql.Connection;
import java.util.Optional;

public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {
    private static final String TABLE_NAME = "lab_schema.users";

    private static final String SELECT_USER_WHERE_EMAIL_AND_PASSWORD = "SELECT * FROM " + TABLE_NAME + " WHERE email=? and password_hash=?";
    private static final String SELECT_USER_WHERE_EMAIL = "SELECT * FROM " + TABLE_NAME + " WHERE email=?";
    private static final String INSERT_USER = "INSERT INTO " + TABLE_NAME + " (email, password_hash, role_id) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM " + TABLE_NAME + " WHERE id=?";

    public UserDAOImpl(Connection connection) {
        super(connection, RowMapperFactory.getInstance().getUserRowMapper(), TABLE_NAME);
    }

    @Override
    public int add(User item) throws DAOException {
        return executeInsert(INSERT_USER, item.getEmail(), item.getPassword(),
                item.getRoleId());
    }

    @Override
    public Optional<User> getByEmail(String email) throws DAOException {
        return executeForSingleResult(SELECT_USER_WHERE_EMAIL, email);
    }

    @Override
    public Optional<User> getByEmailPassword(String email, String password) throws DAOException {
        return executeForSingleResult(SELECT_USER_WHERE_EMAIL_AND_PASSWORD, email, DigestUtils.sha1Hex(password));
    }

    @Override
    public void removeById(int id) throws DAOException {
        executeUpdate(DELETE_USER, id);
    }
}
