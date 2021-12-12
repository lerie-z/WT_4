package server.dao.daos;

import server.dao.DAO;
import server.entity.UserOrder;
import server.exception.DAOException;

import java.sql.Timestamp;
import java.util.List;

public interface UserOrderDAO extends DAO<UserOrder> {
    List<UserOrder> getByStatus(String status) throws DAOException;
    List<UserOrder> getByUserId(int userId) throws DAOException;
    void updateStatusById(long id, String status) throws DAOException;
    List<UserOrder> getByApartmentId(int apartmentId) throws DAOException;
    boolean booked(int number, Timestamp start, Timestamp end) throws DAOException;
}
