package server.command.impl;

import server.command.Command;
import server.command.CommandResult;
import server.command.ResponseType;
import server.command.context.RequestContext;
import server.command.context.RequestManager;
import server.dao.DAOFactory;
import server.entity.Apartment;
import server.entity.User;
import server.entity.UserOrder;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.ServiceFactory;
import server.service.services.ApartmentService;
import server.service.services.UserOrderService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserOrdersCommand implements Command {
    private static final String PAGE = "WEB-INF/view/userOrders.jsp";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String USER_ORDERS = "userOrders";
    private static final String APARTMENTS = "apartments";
    private static final String USER = "user";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();

        User user = (User) requestContext.getSessionAttribute(USER);
        if (user == null) {
            request.updateRequest(requestContext);
            return new CommandResult(PAGE, ResponseType.FORWARD);
        }
        try (DAOFactory daoFactory = new DAOFactory()) {
            ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
            UserOrderService userOrderService = serviceFactory.getUserOrderService();
            List<UserOrder> userOrders=userOrderService.getUserOrderByUserId(user.getId());
            requestContext.addRequestAttribute(USER_ORDERS, userOrders);
            ApartmentService apartmentService = serviceFactory.getApartmentService();
            List<Apartment> apartments = apartmentService.getApartmentsByUserId(user.getId());
            requestContext.addRequestAttribute(APARTMENTS, apartments);

        } catch (ServiceException | DAOException e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        } catch (Exception e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        }

        request.updateRequest(requestContext);
        return new CommandResult(PAGE, ResponseType.FORWARD);
    }
}
