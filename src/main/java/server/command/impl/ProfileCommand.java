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
import server.service.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ProfileCommand implements Command {
    private static final String PAGE = "WEB-INF/view/profile.jsp";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String USER = "user";
    private static final String USER_INFORMATION = "userInformation";

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
            UserService userInformationService = serviceFactory.getUserService();
            Optional<User> userr = userInformationService.getUserById(user.getId());
            userr.ifPresent(information -> requestContext.addRequestAttribute(USER, userr.get()));
        } catch (ServiceException | DAOException e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        } catch (Exception e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        }

        request.updateRequest(requestContext);
        return new CommandResult(PAGE, ResponseType.FORWARD);
    }
}
