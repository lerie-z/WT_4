package server.command;

import server.command.context.RequestManager;
import server.exception.DAOException;

import javax.servlet.http.HttpServletResponse;

public interface Command {
    CommandResult execute(RequestManager request, HttpServletResponse response);
}
