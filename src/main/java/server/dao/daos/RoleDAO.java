package server.dao.daos;

import server.dao.DAO;
import server.entity.Role;
import server.exception.DAOException;

import java.util.Optional;

public interface RoleDAO extends DAO<Role> {
    Optional<Role> getByName(String name) throws DAOException;
}
