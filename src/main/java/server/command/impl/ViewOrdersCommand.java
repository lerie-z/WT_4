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
import server.service.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewOrdersCommand implements Command {
    private static final String PAGE = "WEB-INF/view/orders.jsp";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String USER_ORDERS = "userOrders";
    private static final String USERS = "users";
    private static final String APARTMENTS = "apartments";
    private static final String USER_INFORMATION = "userInformation";
    private static final String EXPECTED = "booked";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();
        try (DAOFactory daoFactory = new DAOFactory()) {
            ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
            UserOrderService userOrderService = serviceFactory.getUserOrderService();
            List<UserOrder> userOrders = userOrderService.getUserOrderByStatus(EXPECTED);
            requestContext.addRequestAttribute(USER_ORDERS, userOrders);

            UserService userService = serviceFactory.getUserService();
            List<User> users = userService.getUsersFromOrders(userOrders);
            requestContext.addRequestAttribute(USERS, users);

            ApartmentService apartmentService = serviceFactory.getApartmentService();
            List<Apartment> apartments=apartmentService.getApartmentsByUserOrders(userOrders);
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
