package server.command.impl;

import server.command.Command;
import server.command.CommandResult;
import server.command.ResponseType;
import server.command.context.RequestContext;
import server.command.context.RequestManager;
import server.dao.DAOFactory;
import server.entity.User;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.ServiceFactory;
import server.service.services.UserOrderService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ConfirmUserOrderCommand implements Command {
    private static final String ADD_ORDER_PAGE = "WEB-INF/view/addUserOrder.jsp";
    private static final String MY_ORDERS_PAGE = "command=userOrders";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DATE = "date";
    private static final String HOUR = "hour";
    private static final String MINUTE="minute";

    private static final String YEAR2 = "year2";
    private static final String MONTH2 = "month2";
    private static final String DATE2 = "date2";
    private static final String HOUR2 = "hour2";
    private static final String MINUTE2="minute2";

    private static final String USER = "user";
    private static final String APARTMENT_ID = "apartment_id";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();
        Optional<String> year = Optional.ofNullable(requestContext.getRequestParameter(YEAR));
        Optional<String> month = Optional.ofNullable(requestContext.getRequestParameter(MONTH));
        Optional<String> date = Optional.ofNullable(requestContext.getRequestParameter(DATE));
        Optional<String> minute = Optional.ofNullable(requestContext.getRequestParameter(MINUTE));
        Optional<String> hour = Optional.ofNullable(requestContext.getRequestParameter(HOUR));
        Optional<String> year2 = Optional.ofNullable(requestContext.getRequestParameter(YEAR2));
        Optional<String> month2 = Optional.ofNullable(requestContext.getRequestParameter(MONTH2));
        Optional<String> date2 = Optional.ofNullable(requestContext.getRequestParameter(DATE2));
        Optional<String> minute2 = Optional.ofNullable(requestContext.getRequestParameter(MINUTE2));
        Optional<String> hour2 = Optional.ofNullable(requestContext.getRequestParameter(HOUR2));

        try (DAOFactory daoFactory = new DAOFactory()) {
            ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
            User user = (User) requestContext.getSessionAttribute(USER);
            int apartmentId=Integer.parseInt(requestContext.getRequestParameter(APARTMENT_ID));
            int userId = user.getId();
            if ( year.isPresent() && month.isPresent() && date.isPresent()
                    && minute.isPresent() && hour.isPresent()
                    && year2.isPresent() && month2.isPresent() && date2.isPresent()
                    && minute2.isPresent() && hour2.isPresent()) {
                UserOrderService userOrderService = serviceFactory.getUserOrderService();
                boolean result = userOrderService.addUserOrder(year.get(), month.get(), date.get(), hour.get(), minute.get(),
                        year2.get(), month2.get(), date2.get(), hour2.get(), minute2.get(), String.valueOf(userId),String.valueOf(apartmentId));
                if (result) {
                    request.updateRequest(requestContext);
                    return new CommandResult(MY_ORDERS_PAGE, ResponseType.REDIRECT);
                }
            }

            requestContext.addRequestAttribute(ERROR_MESSAGE, true);
            request.updateRequest(requestContext);
            return new CommandResult(ADD_ORDER_PAGE, ResponseType.FORWARD);
        } catch (ServiceException | DAOException e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        } catch (Exception e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        }
    }
}
