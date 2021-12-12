package server.connection;

import server.exception.DAOException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static ConnectionPool instance;

    private final Queue<ProxyConnection> availableConnections = new ArrayDeque<>();
    private final Set<ProxyConnection> connectionsInUse = new HashSet<>();

    private final ReentrantLock connectionsLock = new ReentrantLock();
    private final Semaphore semaphore;

    private ConnectionPool() {
        try (InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream("config.properties")){

            Properties properties = new Properties();
            properties.load(propertiesStream);
            String driverName = properties.getProperty("driver.name");
            Class.forName(driverName);

            int connectionCount = Integer.parseInt(properties.getProperty("connection.count"));
            semaphore = new Semaphore(connectionCount);

            String connectionString = properties.getProperty("connection.string");
            String userName = properties.getProperty("user.name");
            String userPassword = properties.getProperty("user.password");

            for (int i = 0; i < connectionCount; i++) {
                Connection connection = DriverManager.getConnection(connectionString, userName, userPassword);
                ProxyConnection proxyConnection = new ProxyConnection(connection, this);
                availableConnections.offer(proxyConnection);
            }

        } catch (IOException e) {
            throw new ConnectionException("Config file not found : " + e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionException("Driver not found : " + e);
        } catch (SQLException e) {
            throw new ConnectionException("Cannot create exception : " + e);
        }
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new ConnectionPool();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    public void returnConnection(ProxyConnection proxyConnection) {
        connectionsLock.lock();
        try {
            if (connectionsInUse.contains(proxyConnection)) {
                availableConnections.offer(proxyConnection);
                connectionsInUse.remove(proxyConnection);
                semaphore.release();
            }
        } finally {
            connectionsLock.unlock();
        }
    }

    public ProxyConnection getConnection() throws DAOException {
        connectionsLock.lock();
        try {
            semaphore.acquire();
            ProxyConnection connection = availableConnections.poll();
            connectionsInUse.add(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new DAOException(e);
        } finally {
            connectionsLock.unlock();
        }
    }
}
