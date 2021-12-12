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
import server.service.services.ApartmentService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ConfirmChangeApartmentStatusCommand implements Command {
    private static final String PAGE = "command=catalog";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String STATUS = "status";
    private static final String APATRMENT_ID = "apartmentId";
    private static final String MESSAGE_PARAMETER = "&message=";
    private static final String ERROR = "error";
    private static final String OK = "ok";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();
        String message = ERROR;

        Optional<String> status = Optional.ofNullable(requestContext.getRequestParameter(STATUS));

        int id = Integer.parseInt(requestContext.getRequestParameter(APATRMENT_ID));


        try (DAOFactory daoFactory = new DAOFactory()) {
            ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
            if (status.isPresent()) {
                ApartmentService apartmentService = serviceFactory.getApartmentService();
                apartmentService.updateApartmentStatusById(id,status.get());
                message = OK;
            }
        } catch (ServiceException | DAOException e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        } catch (Exception e) {
        }

        request.updateRequest(requestContext);
        return new CommandResult(PAGE + MESSAGE_PARAMETER + message, ResponseType.REDIRECT);
    }
}
