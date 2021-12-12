package server.service.impl;

import server.dao.DAOFactory;
import server.dao.daos.RoleDAO;
import server.entity.Role;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.services.RoleService;

import java.util.Optional;

public class RoleServiceImpl implements RoleService {
    private final DAOFactory daoFactory;

    public RoleServiceImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Optional<Role> getRoleById(int roleId) throws ServiceException {
        try {
            RoleDAO roleDao = this.daoFactory.getRoleDAO();
            Optional<Role> result;
            result = roleDao.getById(roleId);
            return result;
        } catch (DAOException e) {

            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Role> getRoleByRoleName(String roleName) throws ServiceException {
        try {
            RoleDAO roleDao = this.daoFactory.getRoleDAO();
            Optional<Role> result;
            result = roleDao.getByName(roleName);
            return result;
        } catch (DAOException e) {

            throw new ServiceException(e.getMessage(), e);
        }
    }
}
