package server.dao.daos;

import server.dao.DAO;
import server.entity.User;
import server.exception.DAOException;

import java.util.Optional;

public interface UserDAO extends DAO<User> {
    Optional<User> getByEmail(String email) throws DAOException;
    Optional<User> getByEmailPassword(String email, String password) throws DAOException;
}
