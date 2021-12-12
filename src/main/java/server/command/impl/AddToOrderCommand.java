package server.command.impl;

import server.command.Command;
import server.command.CommandResult;
import server.command.ResponseType;
import server.command.context.RequestContext;
import server.command.context.RequestManager;
import server.exception.DAOException;

import javax.servlet.http.HttpServletResponse;

public class AddToOrderCommand implements Command {
    private static final String PAGE = "WEB-INF/view/addUserOrder.jsp";
    private static final String APARTMENT_ID = "apartment_id";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();
        request.updateRequest(requestContext);
        return new CommandResult(PAGE, ResponseType.FORWARD);
    }
}
