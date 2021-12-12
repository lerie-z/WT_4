package server.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import server.dao.DAOFactory;
import server.dao.daos.RoleDAO;
import server.dao.daos.UserDAO;
import server.entity.Role;
import server.entity.User;
import server.entity.UserOrder;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.services.UserService;
import server.service.validator.Validator;
import server.service.validator.ValidatorFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final String USER = "user";
    private final DAOFactory daoFactory;

    public UserServiceImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        if (email == null || password == null) {
            return Optional.empty();
        }

        if (!isEmailValid(email)) {
            return Optional.empty();
        }

        try {
            UserDAO userDao = this.daoFactory.getUserDAO();
            return userDao.getByEmailPassword(email, password);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean register(String email, String password) throws ServiceException {
        if (email == null || password == null) {
            return false;
        }

        if (!(isEmailValid(email))) {
            return false;
        }
        try {
            UserDAO userDao = this.daoFactory.getUserDAO();
            if (userDao.getByEmail(email).isPresent()) {
                return false;
            }

            RoleDAO roleDao = this.daoFactory.getRoleDAO();
            Optional<Role> role = roleDao.getByName(USER);
            if (!role.isPresent()) {
                return false;
            }

            User user = createUser(email, password, role.get().getId());
            userDao.add(user);

            return true;
        } catch (DAOException e) {

            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> getUserById(int userId) throws ServiceException {
        try {
            UserDAO userDao = this.daoFactory.getUserDAO();
            Optional<User> result;
            result = userDao.getById(userId);
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getUsersFromOrders(List<UserOrder> orders) throws ServiceException {
        List<User> users = new LinkedList<>();
        try {
            for (UserOrder order : orders) {
                Optional<User> user = getUserById(order.getUserId());
                if (user.isPresent()) {
                    if (!users.contains(user.get())) {
                        users.add(user.get());
                    }
                }
            }
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return users;
    }

    private boolean isEmailValid(String email) {
        Validator validator = (new ValidatorFactory()).getEmailValidator();
        return validator.isValid(email);
    }

    private User createUser(String email, String password, int roleId) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(DigestUtils.sha1Hex(password));
        user.setRoleId(roleId);
        return user;
    }
}
