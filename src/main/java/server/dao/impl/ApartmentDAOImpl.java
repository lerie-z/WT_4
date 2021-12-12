package server.dao.impl;

import server.dao.AbstractDAO;
import server.dao.daos.ApartmentDAO;
import server.entity.Apartment;
import server.exception.DAOException;
import server.mapper.RowMapper;
import server.mapper.RowMapperFactory;

import java.sql.Connection;
import java.util.List;

public class ApartmentDAOImpl extends AbstractDAO<Apartment> implements ApartmentDAO {
    private static final String TABLE_NAME = "apartments";

    private static final String SELECT_APARTMENTS_WHERE_STATUS = "SELECT * FROM apartments WHERE status=? ";

    private static final String SELECT_APARTMENTS_WHERE_PRICE = "SELECT * FROM apartments WHERE price BETWEEN ? AND ? ";

    private static final String SELECT_APARTMENTS_WHERE_NUMBER_OF_ROOMS = "SELECT * FROM apartments WHERE rooms_number=? ";

    private static final String INSERT_APARTMENT = "INSERT INTO apartments (status, rooms_number, number, price) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_APARTMENT_WHERE_ID = "UPDATE apartments SET status=? rooms_number=? number=? price=? WHERE id=?";

    private static final String UPDATE_APARTMENT_STATUS_WHERE_ID = "UPDATE apartments SET status=?  WHERE id=?";

    public ApartmentDAOImpl(Connection connection) {
        super(connection, RowMapperFactory.getInstance().getApartmentRowMapper(), TABLE_NAME);
    }

    @Override
    public int add(Apartment item) throws DAOException {
        return executeInsert(INSERT_APARTMENT, item.getStatus(), item.getRoomsNumber(), item.getNumber(), item.getPrice());
    }

    @Override
    public List<Apartment> getByPrice(double from, double to) throws DAOException {
        return executeQuery(SELECT_APARTMENTS_WHERE_PRICE, from, to);
    }

    @Override
    public List<Apartment> getByRoomsNumber(int number) throws DAOException {
        return executeQuery(SELECT_APARTMENTS_WHERE_NUMBER_OF_ROOMS, number);
    }

    @Override
    public List<Apartment> getByStatus(String status) throws DAOException {
        return executeQuery(SELECT_APARTMENTS_WHERE_STATUS, status);
    }

    @Override
    public void updateStatus(int id, String status) throws DAOException {
        executeUpdate(UPDATE_APARTMENT_STATUS_WHERE_ID, status, id);
    }

    @Override
    public void updateApartment(Apartment apartment) throws DAOException {
        executeUpdate(UPDATE_APARTMENT_WHERE_ID, apartment, apartment.getId());
    }
}
