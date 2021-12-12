package server.service.services;

import server.entity.UserOrder;
import server.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserOrderService {
    Optional<UserOrder> getUserOrderById(int userOrderId) throws ServiceException;

    List<UserOrder> getUserOrderByUserId(int userId) throws ServiceException;

    List<UserOrder> getUserOrderByStatus(String status) throws ServiceException;

    boolean updateStatusAtUserOrderById(int userOrderId, String status) throws ServiceException;

    boolean addUserOrder(String stringYear, String stringMonth, String stringDate,String stringHours,
                            String stringMinutes, String stringYear2, String stringMonth2, String stringDate2, String stringHours2, String stringMinutes2, String stringUserId, String stringApartmentId) throws ServiceException;
}
