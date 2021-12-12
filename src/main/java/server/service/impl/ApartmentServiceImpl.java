package server.service.impl;

import server.dao.DAOFactory;
import server.dao.daos.ApartmentDAO;
import server.dao.daos.UserOrderDAO;
import server.entity.Apartment;
import server.entity.UserOrder;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.services.ApartmentService;
import server.service.validator.Validator;
import server.service.validator.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApartmentServiceImpl implements ApartmentService {
    private final DAOFactory daoFactory;

    public ApartmentServiceImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<Apartment> getAllApartments() throws ServiceException {
        try {
            ApartmentDAO apartmentDAO = this.daoFactory.getApartmentDAO();
            List<Apartment> result;
            result = apartmentDAO.getAll();
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Apartment> getApartmentById(int id) throws ServiceException {
        try {
            ApartmentDAO apartmentDAO = this.daoFactory.getApartmentDAO();
            Optional<Apartment> result;
            result = apartmentDAO.getById(id);
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Apartment> getApartmentsByUserId(int userId) throws ServiceException {
        try {
            UserOrderDAO userOrderDao = this.daoFactory.getUserOrderDAO();
            List<UserOrder> userOrders = userOrderDao.getByUserId(userId);
            List<Apartment> result = new ArrayList<>();
            ApartmentDAO apartmentDao = this.daoFactory.getApartmentDAO();
            for (UserOrder userOrder : userOrders) {
                result.add(apartmentDao.getById(userOrder.getApartmentId()).get());
            }
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void removeApartmentById(int id) throws ServiceException {
        try {
            ApartmentDAO apartmentDAO = this.daoFactory.getApartmentDAO();
            UserOrderDAO userOrderDAO =this.daoFactory.getUserOrderDAO();

            List<UserOrder> userOrders=userOrderDAO.getByApartmentId(id);
            for (UserOrder userOrder : userOrders) {
                userOrderDAO.removeById(userOrder.getId());
            }

            apartmentDAO.removeById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Apartment> getApartmentByStatus(String status) throws ServiceException {
        try {
            ApartmentDAO apartmentDao= this.daoFactory.getApartmentDAO();
            List<Apartment> result = null;
            result = apartmentDao.getByStatus(status);
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Apartment> getApartmentsByUserOrders(List<UserOrder> userOrders) throws ServiceException {
        try {
            ApartmentDAO apartmentDao= this.daoFactory.getApartmentDAO();

            List<Apartment> result = new ArrayList<>();
            for (UserOrder userOrder : userOrders) {
                result.add(apartmentDao.getById(userOrder.getApartmentId()).get());
            }

            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean addApartment(String status, String roomsNumberStr, String numberStr, String priceStr) throws ServiceException {
        if(status == null || numberStr == null || roomsNumberStr == null || priceStr == null){
            return false;
        }

        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator priceValidator= validatorFactory.getPriceValidator();

        if (!(priceValidator.isValid(priceStr))) {
            return false;
        }

        int roomsNumber = Integer.parseInt(roomsNumberStr);
        int number = Integer.parseInt(numberStr);
        double price = Double.parseDouble(priceStr);

        ApartmentDAO apartmentDao = this.daoFactory.getApartmentDAO();
        Apartment apartment = createApartment(status, roomsNumber, number, price);
        try {
            apartmentDao.add(apartment);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateApartmentStatusById(int id, String status) throws ServiceException {
        ApartmentDAO apartmentDao = this.daoFactory.getApartmentDAO();
        try {
            apartmentDao.updateStatus(id, status);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean updateApartment(String idStr, String status, String roomsNumberStr, String numberStr, String priceStr) throws ServiceException {
        if(status == null || numberStr == null || roomsNumberStr == null || priceStr == null){
            return false;
        }

        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator priceValidator = validatorFactory.getPriceValidator();
        Validator idValidator = validatorFactory.getIdValidator();

        if(!(priceValidator.isValid(priceStr) && idValidator.isValid(idStr))){
            return false;
        }

        int id = Integer.parseInt(idStr);
        int roomsNumber = Integer.parseInt(roomsNumberStr);
        int number = Integer.parseInt(numberStr);
        double price = Double.parseDouble(priceStr);

        ApartmentDAO apartmentDAO = this.daoFactory.getApartmentDAO();
        Apartment apartment = createApartment(status, roomsNumber, number, price);
        apartment.setId(id);
        try {
            apartmentDAO.updateApartment(apartment);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Apartment createApartment(String status, int roomsNumber, int number, double  price){
        Apartment apartment = new Apartment();
        apartment.setStatus(status);
        apartment.setRoomsNumber(roomsNumber);
        apartment.setNumber(number);
        apartment.setPrice(price);
        return apartment;
    }
}
