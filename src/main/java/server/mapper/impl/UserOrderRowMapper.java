package server.mapper.impl;

import server.entity.UserOrder;
import server.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserOrderRowMapper implements RowMapper<UserOrder> {
    @Override
    public UserOrder map(ResultSet resultSet) throws SQLException {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(resultSet.getInt("id"));
        userOrder.setStatus(resultSet.getString("status"));
        userOrder.setStartTime(resultSet.getTimestamp("start_time"));
        userOrder.setEndTime(resultSet.getTimestamp("end_time"));
        userOrder.setUserId(resultSet.getInt("user_id"));
        userOrder.setApartmentId(resultSet.getInt("apartment_id"));
        return userOrder;
    }
}
