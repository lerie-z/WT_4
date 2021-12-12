package server.service.services;

import server.entity.User;
import server.entity.UserOrder;
import server.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String email, String password) throws ServiceException;
    boolean register(String email, String password) throws ServiceException;
    Optional<User> getUserById(int userId) throws ServiceException;
    public List<User> getUsersFromOrders(List<UserOrder> orders) throws ServiceException;
}
