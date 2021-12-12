package server.dao;

import server.entity.Identifiable;
import server.exception.DAOException;
import server.mapper.RowMapper;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<T extends Identifiable> implements DAO<T>{
    private final Connection connection;
    private final RowMapper<T> mapper;
    private final String tableName;

    public AbstractDAO(Connection connection, RowMapper<T> mapper, String tableName) {
        this.connection = connection;
        this.mapper = mapper;
        this.tableName = tableName;
    }

    protected List<T> executeQuery(String query, Object... params) throws DAOException {
        List<T> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = createStatement(query, params);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    protected PreparedStatement createStatement(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }

    protected PreparedStatement createUpdateStatement(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }

    protected Optional<T> executeForSingleResult(String query, Object... params) throws DAOException {
        List<T> entities = this.executeQuery(query, params);
        if (entities.size() > 1) {
            throw new DAOException("There are more then one records in the table");
        } else if (entities.size() == 1) {
            return Optional.of(entities.get(0));
        } else {
            return Optional.empty();
        }
    }

    protected void executeUpdate(String query, Object... params) throws DAOException {
        try (PreparedStatement statement = createUpdateStatement(query, params)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    protected int executeInsert(String query, Object... params) throws DAOException {
        int result = 0;
        try (PreparedStatement statement = createUpdateStatement(query, params)) {
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                result = generatedKey.getInt(1);
            }
        } catch (SQLException e) {

            throw new DAOException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<T> getAll() throws DAOException {
        String query = "SELECT * FROM " + tableName;
        return this.executeQuery(query);
    }

    @Override
    public Optional<T> getById(int id) throws DAOException {
        String query = "SELECT * FROM " + tableName + " WHERE id=?";
        return this.executeForSingleResult(query, id);
    }

    @Override
    public void removeById(int id) throws DAOException {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE id=?";
        this.executeUpdate(deleteQuery, id);
    }
}
