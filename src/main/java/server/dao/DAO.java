package server.dao;

import server.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    List<T> getAll() throws DAOException;
    Optional<T> getById(int id) throws DAOException;
    int add(T item) throws DAOException;
    void removeById(int id) throws DAOException;
}
