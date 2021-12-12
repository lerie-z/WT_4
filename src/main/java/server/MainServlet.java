package server;

import server.command.Command;
import server.command.CommandFactory;
import server.command.CommandResult;
import server.command.context.RequestManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private static final CommandFactory commandFactory = CommandFactory.getInstance();
    private static final String CURRENT_URL = "/booking?";
    private static final String DEFAULT_COMMAND = "command=home";
    private static final String COMMAND_PARAM = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND_PARAM);

        if (commandName == null || "".equals(commandName)) {
            response.sendRedirect(CURRENT_URL + DEFAULT_COMMAND);
        } else {
            Command command = CommandFactory.getInstance().getCommand(commandName);
            RequestManager contextHelper = new RequestManager(request);

            CommandResult result = command.execute(contextHelper, response);
            dispatch(result, request, response);
        }
    }

    private void dispatch(CommandResult result, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (result.isRedirect()) {
            response.sendRedirect(CURRENT_URL + result.getPage());
        } else {
            request.getRequestDispatcher(result.getPage()).forward(request, response);
        }
    }
}