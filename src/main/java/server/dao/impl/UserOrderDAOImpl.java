package server.dao.impl;

import server.dao.AbstractDAO;
import server.dao.daos.UserOrderDAO;
import server.entity.UserOrder;
import server.exception.DAOException;
import server.mapper.RowMapperFactory;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class UserOrderDAOImpl extends AbstractDAO<UserOrder> implements UserOrderDAO {
    private static final String TABLE_NAME = "userorders";

    private static final String SELECT_USER_ORDERS_WHERE_STATUS = "SELECT * FROM " + TABLE_NAME + " WHERE status=?";
    private static final String UPDATE_USER_ORDER_STATUS_WHERE_ID = "UPDATE " + TABLE_NAME + " SET status=? WHERE id=?";
    private static final String INSERT_USER_ORDER = "INSERT INTO " + TABLE_NAME + " (status, start_time, end_time,  user_id, apartment_id) VALUES (?, ?, ?, ?,?)";
    private static final String SELECT_USER_ORDERS_WHERE_USER_ID="SELECT * FROM " + TABLE_NAME + " WHERE user_id=?";
    private static final String SELECT_USER_ORDERS_WHERE_APARTMENT_ID="SELECT * FROM " + TABLE_NAME + " WHERE apartment_id=?";
    private static final String SELECT_BOOKED="SELECT * FROM " + TABLE_NAME + " WHERE (apartment_id=?) & (((? >= start_time) & (? <= end_time)) | ((? <= start_time) & (? >= end_time)) | ((? >= start_time) & (? <= end_time)) | ((? >= start_time) & (? <= end_time)))";

    public UserOrderDAOImpl(Connection connection) {
        super(connection, RowMapperFactory.getInstance().getUserOrderRowMapper(), TABLE_NAME);
    }

    @Override
    public int add(UserOrder item) throws DAOException {
        return executeInsert(INSERT_USER_ORDER, item.getStatus(), item.getStartTime(),
                item.getEndTime(), item.getUserId(), item.getApartmentId());
    }

    public boolean booked(int number, Timestamp start, Timestamp end) throws DAOException {
        List<UserOrder> orders = executeQuery(SELECT_BOOKED, number, start, end, start, end, start, start, end, end);
        return !orders.isEmpty();
    }

    @Override
    public List<UserOrder> getByStatus(String status) throws DAOException {
        return executeQuery(SELECT_USER_ORDERS_WHERE_STATUS, status);
    }

    @Override
    public List<UserOrder> getByUserId(int userId) throws DAOException {
        return executeQuery(SELECT_USER_ORDERS_WHERE_USER_ID, userId);
    }

    @Override
    public void updateStatusById(long id, String status) throws DAOException {
        executeUpdate(UPDATE_USER_ORDER_STATUS_WHERE_ID, status, id);
    }

    @Override
    public List<UserOrder> getByApartmentId(int apartmentId) throws DAOException {
        return executeQuery(SELECT_USER_ORDERS_WHERE_APARTMENT_ID, apartmentId);
    }
}
