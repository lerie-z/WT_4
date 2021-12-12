package server.command.impl;

import server.command.Command;
import server.command.CommandResult;
import server.command.ResponseType;
import server.command.context.RequestContext;
import server.command.context.RequestManager;

import javax.servlet.http.HttpServletResponse;

public class SignoutCommand implements Command  {
    private static final String PAGE = "command=signin";
    private static final String USER = "user";
    private static final String ROLE = "role";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();
        requestContext.removeSessionAttribute(USER);
        requestContext.removeSessionAttribute(ROLE);
        request.updateRequest(requestContext);
        return new CommandResult(PAGE, ResponseType.REDIRECT);
    }
}
