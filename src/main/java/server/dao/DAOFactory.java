package server.dao;

import server.dao.daos.ApartmentDAO;
import server.dao.daos.RoleDAO;
import server.dao.daos.UserDAO;
import server.dao.daos.UserOrderDAO;
import server.dao.impl.ApartmentDAOImpl;
import server.dao.impl.RoleDAOImpl;
import server.dao.impl.UserDAOImpl;
import server.dao.impl.UserOrderDAOImpl;
import server.connection.ProxyConnection;
import server.connection.ConnectionPool;
import server.exception.DAOException;

public class DAOFactory implements AutoCloseable {
    private final ProxyConnection connection;

    public UserDAO getUserDAO() {
        return new UserDAOImpl(connection);
    }

    public RoleDAO getRoleDAO() {
        return new RoleDAOImpl(connection);
    }

    public UserOrderDAO getUserOrderDAO() {
        return new UserOrderDAOImpl(connection);
    }

    public ApartmentDAO getApartmentDAO() {
        return new ApartmentDAOImpl(connection);
    }

    public DAOFactory() throws DAOException {
        this.connection = ConnectionPool.getInstance().getConnection();
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
