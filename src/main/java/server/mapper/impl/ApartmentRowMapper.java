package server.mapper.impl;

import server.entity.Apartment;
import server.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApartmentRowMapper implements RowMapper<Apartment> {
    @Override
    public Apartment map(ResultSet resultSet) throws SQLException {
        Apartment apartment = new Apartment();
        apartment.setId(resultSet.getInt("id"));
        apartment.setPrice(resultSet.getDouble("price"));
        apartment.setStatus(resultSet.getString("status"));
        apartment.setNumber(resultSet.getInt("number"));
        apartment.setRoomsNumber(resultSet.getInt("rooms_number"));
        return apartment;
    }
}
