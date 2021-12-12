package server.service.impl;

import server.dao.DAOFactory;
import server.dao.daos.UserOrderDAO;
import server.entity.UserOrder;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.services.UserOrderService;
import server.service.validator.Validator;
import server.service.validator.ValidatorFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class UserOrderServiceImpl implements UserOrderService {
    private  static final String STATUS = "booked";
    private final DAOFactory daoFactory;

    public UserOrderServiceImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Optional<UserOrder> getUserOrderById(int userOrderId) throws ServiceException {
        try {
            UserOrderDAO userOrderDao = this.daoFactory.getUserOrderDAO();
            Optional<UserOrder> result;
            result = userOrderDao.getById(userOrderId);
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<UserOrder> getUserOrderByUserId(int userId) throws ServiceException {
        try {
            UserOrderDAO userOrderDao = this.daoFactory.getUserOrderDAO();
            List<UserOrder> result;
            result = userOrderDao.getByUserId(userId);
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<UserOrder> getUserOrderByStatus(String status) throws ServiceException {
        try {
            UserOrderDAO userOrderDao = this.daoFactory.getUserOrderDAO();
            List<UserOrder> result = null;
            result = userOrderDao.getByStatus(status);
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean updateStatusAtUserOrderById(int userOrderId, String status) throws ServiceException {
        try {
            UserOrderDAO userOrderDao = this.daoFactory.getUserOrderDAO();
            Optional<UserOrder> userOrder = userOrderDao.getById(userOrderId);
            if (!userOrder.isPresent()) {
                return false;
            }
            userOrderDao.updateStatusById(userOrderId, status);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean addUserOrder(String stringYear, String stringMonth, String stringDate, String stringHours, String stringMinutes, String stringYear2, String stringMonth2, String stringDate2, String stringHours2, String stringMinutes2, String stringUserId, String stringApartmentId) throws ServiceException {
        if ( stringYear == null  || stringMonth == null || stringHours == null ||
                stringYear2 == null  || stringMonth2 == null || stringHours2 == null || stringUserId == null || stringApartmentId == null ) {
            return false;
        }

        ValidatorFactory validatorFactory = new ValidatorFactory();
        Validator monthValidator = validatorFactory.getMonthValidator();
        Validator yearValidator = validatorFactory.getYearValidator();
        Validator dayValidator = validatorFactory.getDateValidator();
        Validator minuteValidator = validatorFactory.getMinuteValidator();
        Validator idValidator = validatorFactory.getIdValidator();
        if (!( monthValidator.isValid(stringMonth) && yearValidator.isValid(stringYear) &&
                dayValidator.isValid(stringDate) &&  minuteValidator.isValid(stringMinutes) &&
                monthValidator.isValid(stringMonth2) && yearValidator.isValid(stringYear2) &&
                dayValidator.isValid(stringDate2) &&  minuteValidator.isValid(stringMinutes2)
                && idValidator.isValid(stringUserId) && idValidator.isValid(stringApartmentId))) {
            return false;
        }

        int year = Integer.parseInt(stringYear);
        int month = Integer.parseInt(stringMonth);
        int day = Integer.parseInt(stringDate);
        int hours = Integer.parseInt(stringHours);
        int minutes = Integer.parseInt(stringMinutes);
        int year2 = Integer.parseInt(stringYear2);
        int month2 = Integer.parseInt(stringMonth2);
        int day2 = Integer.parseInt(stringDate2);
        int hours2 = Integer.parseInt(stringHours2);
        int minutes2 = Integer.parseInt(stringMinutes2);
        int userId=Integer.parseInt(stringUserId);
        int apartmentId = Integer.parseInt(stringApartmentId);

        Timestamp currentDateTime=new Timestamp(System.currentTimeMillis());
        Timestamp orderDateTime = new Timestamp(year - 1900, month - 1, day, hours, minutes, 0, 0);
        Timestamp endDateTime = new Timestamp(year2 - 1900, month2 - 1, day2, hours2, minutes2, 0, 0);

        if(!isDateTimeValid(currentDateTime,orderDateTime)){
            return false;
        }

        try {
            UserOrder userOrder = createUserOrder(STATUS, orderDateTime, endDateTime, userId, apartmentId);
            UserOrderDAO userOrderDao = this.daoFactory.getUserOrderDAO();
            if (userOrderDao.booked(apartmentId, orderDateTime, endDateTime)) return false;
            userOrderDao.add(userOrder);
            return true;
        } catch (DAOException e) {

            throw new ServiceException(e.getMessage(), e);
        }
    }

    private boolean isDateTimeValid(Timestamp currentDateTime, Timestamp orderDateTime) {
        if(currentDateTime.compareTo(orderDateTime)>0){
            return false;
        }
        return true;
    }



    private UserOrder createUserOrder(String status, Timestamp startTime, Timestamp endTime, int userId, int apartmentId) {
        UserOrder userOrder = new UserOrder();
        userOrder.setStatus(status);
        userOrder.setStartTime(startTime);
        userOrder.setEndTime(endTime);
        userOrder.setUserId(userId);
        userOrder.setApartmentId(apartmentId);
        return userOrder;
    }
}
