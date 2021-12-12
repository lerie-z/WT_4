package server.mapper;

import server.entity.Apartment;
import server.entity.Role;
import server.entity.User;
import server.entity.UserOrder;
import server.mapper.impl.ApartmentRowMapper;
import server.mapper.impl.RoleRowMapper;
import server.mapper.impl.UserOrderRowMapper;
import server.mapper.impl.UserRowMapper;

public class RowMapperFactory {
    static final RowMapperFactory instance = new RowMapperFactory();

    public static RowMapperFactory getInstance() {
        return RowMapperFactory.instance;
    }

    public RowMapper<User> getUserRowMapper() {
        return new UserRowMapper();
    }

    public RowMapper<Role> getRoleRowMapper() {
        return new RoleRowMapper();
    }

    public RowMapper<UserOrder> getUserOrderRowMapper() {
        return new UserOrderRowMapper();
    }

    public RowMapper<Apartment> getApartmentRowMapper() {
        return new ApartmentRowMapper();
    }
}
