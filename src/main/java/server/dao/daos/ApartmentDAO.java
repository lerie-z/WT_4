package server.dao.daos;

import server.dao.DAO;
import server.entity.Apartment;
import server.exception.DAOException;

import java.util.List;

public interface ApartmentDAO extends DAO<Apartment> {
    List<Apartment> getByPrice(double from, double to) throws DAOException;
    List<Apartment> getByRoomsNumber(int number) throws DAOException;
    List<Apartment> getByStatus(String status) throws DAOException;
    void updateStatus(int id, String status) throws DAOException;
    void updateApartment(Apartment apartment) throws DAOException;
}
