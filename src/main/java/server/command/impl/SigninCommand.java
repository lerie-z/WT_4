package server.command.impl;

import server.command.Command;
import server.command.CommandResult;
import server.command.ResponseType;
import server.command.context.RequestContext;
import server.command.context.RequestManager;
import server.dao.DAOFactory;
import server.entity.Role;
import server.entity.User;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.ServiceFactory;
import server.service.services.RoleService;
import server.service.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SigninCommand implements Command {
    private static final String PROFILE_PAGE = "command=profile";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String PAGE = "WEB-INF/view/signin.jsp";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";
    private static final String USER = "user";
    private static final String ROLE = "role";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();
        String password = requestContext.getRequestParameter(PASSWORD_PARAMETER);
        String login = requestContext.getRequestParameter(EMAIL_PARAMETER);
        try (DAOFactory daoFactory = new DAOFactory()) {
            ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
            UserService userService = serviceFactory.getUserService();
            Optional<User> optionalResult = userService.login(login, password);
            if (optionalResult.isPresent()) {
                requestContext.addSessionAttribute(USER, optionalResult.get());
                RoleService roleService = serviceFactory.getRoleService();
                Optional<Role> role = roleService.getRoleById(optionalResult.get().getRoleId());
                role.ifPresent(value -> requestContext.addSessionAttribute(ROLE, value));
                request.updateRequest(requestContext);
                return new CommandResult(PROFILE_PAGE, ResponseType.REDIRECT);
            }
        } catch (ServiceException | DAOException e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        } catch (Exception e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        }

        requestContext.addRequestAttribute(ERROR_MESSAGE, true);
        request.updateRequest(requestContext);
        return new CommandResult(PAGE, ResponseType.FORWARD);
    }
}
