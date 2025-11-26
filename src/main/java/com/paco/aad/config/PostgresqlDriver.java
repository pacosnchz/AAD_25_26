package com.paco.aad.config;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class PostgresqlDriver {

    private final DataSource dataSource;
    private Connection connection;

    public PostgresqlDriver(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // ------------------------------------------------------
    // BEGIN TRANSACTION
    // ------------------------------------------------------
    public void beginTransaction() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return; // ya hay una transacción abierta
        }

        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
    }

    // ------------------------------------------------------
    // COMMIT
    // ------------------------------------------------------
    public void commit() throws SQLException {
        if (connection != null) {
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
            connection = null;
        }
    }

    // ------------------------------------------------------
    // ROLLBACK
    // ------------------------------------------------------
    public void rollback() throws SQLException {
        if (connection != null) {
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
            connection = null;
        }
    }

    // ------------------------------------------------------
    // GET CONNECTION for transactional operations
    // ------------------------------------------------------
    public Connection getConnection() {
        return connection;
    }
}
