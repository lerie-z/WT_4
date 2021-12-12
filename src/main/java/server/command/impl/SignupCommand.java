package server.command.impl;

import org.apache.commons.codec.digest.DigestUtils;
import server.command.Command;
import server.command.CommandResult;
import server.command.ResponseType;
import server.command.context.RequestContext;
import server.command.context.RequestManager;
import server.dao.DAOFactory;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.ServiceFactory;
import server.service.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SignupCommand implements Command {
    private static final String PAGE = "WEB-INF/view/signup.jsp";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String EMAIL = "email";
    private static final String PASSWORD_FIRST = "password-first";
    private static final String PASSWORD_SECOND = "password-second";
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";
    private static final String OK = "ok";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();
        String message = ERROR;
        Optional<String> email = Optional.ofNullable(requestContext.getRequestParameter(EMAIL));
        Optional<String> passwordFirst = Optional.ofNullable(requestContext.getRequestParameter(PASSWORD_FIRST));
        Optional<String> passwordSecond = Optional.ofNullable(requestContext.getRequestParameter(PASSWORD_SECOND));

        try (DAOFactory daoFactory = new DAOFactory()) {
            ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
            if (email.isPresent() && passwordFirst.isPresent() && passwordSecond.isPresent() &&
                    passwordFirst.get().equals(passwordSecond.get())) {

                    UserService userService = serviceFactory.getUserService();
                    boolean result = userService.register(email.get(), passwordFirst.get());
                    if (result) message = OK;
            }
        } catch (ServiceException | DAOException e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        } catch (Exception e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        }

        requestContext.addRequestAttribute(MESSAGE, message);
        request.updateRequest(requestContext);
        return new CommandResult(PAGE, ResponseType.FORWARD);
    }
}
