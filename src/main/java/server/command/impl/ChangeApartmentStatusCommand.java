package server.command.impl;

import server.command.Command;
import server.command.CommandResult;
import server.command.ResponseType;
import server.command.context.RequestContext;
import server.command.context.RequestManager;

import javax.servlet.http.HttpServletResponse;

public class ChangeApartmentStatusCommand implements Command {
    private static final String PAGE = "WEB-INF/view/changeApartmentStatus.jsp";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();
        request.updateRequest(requestContext);
        return new CommandResult(PAGE, ResponseType.FORWARD);
    }
}
