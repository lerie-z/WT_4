package server.service.services;

import server.entity.Role;
import server.exception.ServiceException;

import java.util.Optional;

public interface RoleService {
    Optional<Role> getRoleById(int roleId) throws ServiceException;
    Optional<Role> getRoleByRoleName(String roleName) throws ServiceException;
}
