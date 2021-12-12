package server.command.impl;

import server.command.Command;
import server.command.CommandResult;
import server.command.ResponseType;
import server.command.context.RequestContext;
import server.command.context.RequestManager;
import server.dao.DAOFactory;
import server.entity.Apartment;
import server.entity.User;
import server.exception.DAOException;
import server.exception.ServiceException;
import server.service.ServiceFactory;
import server.service.services.ApartmentService;
import server.service.services.RoleService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CatalogCommand implements Command {
    private static final String PAGE = "WEB-INF/view/catalog.jsp";
    private static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private static final String APARTMENTS = "apartments";
    private static final String STATUS = "available";
    private static final String USER = "user";
    private static final String ADMIN_ROLE = "admin";

    @Override
    public CommandResult execute(RequestManager request, HttpServletResponse response) {
        RequestContext requestContext = request.createContext();

        User user = (User) requestContext.getSessionAttribute(USER);
        if (user == null) {
            try (DAOFactory daoFactory = new DAOFactory()) {
                ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
                request.updateRequest(requestContext);
                ApartmentService apartmentService = serviceFactory.getApartmentService();
                List<Apartment> apartment=apartmentService.getApartmentByStatus(STATUS);
                requestContext.addRequestAttribute(APARTMENTS,apartment);
                request.updateRequest(requestContext);
            } catch (ServiceException | DAOException e) {
                return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
            } catch (Exception e) {
            }
            return new CommandResult(PAGE, ResponseType.FORWARD);
        }

        try (DAOFactory daoFactory = new DAOFactory()) {
            ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
            RoleService roleService = serviceFactory.getRoleService();
            ApartmentService apartmentService = serviceFactory.getApartmentService();
            List<Apartment> apartment;
            if (roleService.getRoleById(user.getRoleId()).get().getName().equals(ADMIN_ROLE)){
                apartment = apartmentService.getAllApartments();
            }else {
                apartment = apartmentService.getApartmentByStatus(STATUS);
            }
            requestContext.addRequestAttribute(APARTMENTS,apartment);

        } catch (ServiceException | DAOException e) {
            return new CommandResult(ERROR_PAGE, ResponseType.FORWARD);
        } catch (Exception e) {
        }

        request.updateRequest(requestContext);
        return new CommandResult(PAGE, ResponseType.FORWARD);
    }
}
