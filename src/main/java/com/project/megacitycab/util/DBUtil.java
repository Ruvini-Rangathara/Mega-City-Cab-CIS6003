package com.project.megacitycab.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil {
    private static final Logger logger = Logger.getLogger(DBUtil.class.getName());
    private static final DataSource dataSource;

    private DBUtil() {}

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/MySQLDB");
        } catch (NamingException e) {
            logger.log(Level.SEVERE, "Failed to initialize DataSource", e);
            throw new RuntimeException("Failed to initialize DataSource", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false); // Set default auto-commit to false
            return connection;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get database connection", e);
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Failed to close connection", e);
            }
        }
    }

    public static void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Failed to rollback transaction", e);
            }
        }
    }
}
