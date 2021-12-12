package server.service.services;

import server.entity.Apartment;
import server.entity.UserOrder;
import server.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ApartmentService {
    List<Apartment> getAllApartments() throws ServiceException;

    Optional<Apartment> getApartmentById(int id) throws ServiceException;

    List<Apartment> getApartmentsByUserId(int userId) throws ServiceException;

    void removeApartmentById(int id) throws ServiceException;

    List<Apartment> getApartmentByStatus(String status) throws ServiceException;

    List<Apartment> getApartmentsByUserOrders(List<UserOrder> userOrders) throws ServiceException;

    boolean addApartment(String status, String roomsNumberStr, String numberStr, String priceStr) throws ServiceException;

    void updateApartmentStatusById(int id, String status) throws ServiceException;

    boolean updateApartment(String idStr, String status, String roomsNumberStr, String numberStr, String priceStr) throws ServiceException;
}
