package server.service;

import server.dao.DAOFactory;
import server.service.impl.ApartmentServiceImpl;
import server.service.impl.RoleServiceImpl;
import server.service.impl.UserOrderServiceImpl;
import server.service.impl.UserServiceImpl;
import server.service.services.ApartmentService;
import server.service.services.RoleService;
import server.service.services.UserOrderService;
import server.service.services.UserService;

public class ServiceFactory {
    private final DAOFactory daoFactory;

    public ServiceFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public ApartmentService getApartmentService() {
        return new ApartmentServiceImpl(this.daoFactory);
    }

    public RoleService getRoleService() {
        return new RoleServiceImpl(this.daoFactory);
    }

    public UserService getUserService() {
        return new UserServiceImpl(this.daoFactory);
    }

    public UserOrderService getUserOrderService() {
        return new UserOrderServiceImpl(this.daoFactory);
    }
}
