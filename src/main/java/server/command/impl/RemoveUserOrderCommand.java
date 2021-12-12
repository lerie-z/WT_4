package server.command.impl;

import server.command.Command;
import server.command.CommandResult;
import server.command.ResponseType;
import server.command.context.RequestContext;
import server.command.context.RequestManager;
import server.dao.DAOFactory;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.ServiceFactory;
import server.service.services.UserOrderService;

import javax.servlet.http.HttpServletResponse;

public class RemoveUserOrderCommand implements Command {
    private static final String PAGE = "command=orders";
    private static final String USER_ORDER_ID = "userOrderId";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String CANCELED = "canceled";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();
        try (DAOFactory daoFactory = new DAOFactory()) {
            ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
            int userOrderId = Integer.parseInt(requestContext.getRequestParameter(USER_ORDER_ID));
            UserOrderService userOrderService = serviceFactory.getUserOrderService();
            userOrderService.updateStatusAtUserOrderById(userOrderId, CANCELED);
        } catch (ServiceException | DAOException e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        } catch (Exception e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        }

        request.updateRequest(requestContext);
        return new CommandResult(PAGE, ResponseType.REDIRECT);
    }
}
