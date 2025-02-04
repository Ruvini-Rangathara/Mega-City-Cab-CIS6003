package com.project.megacitycab.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static final DataSource dataSource; // Singleton instance of DataSource

    // Private constructor to prevent instantiation
    private DBUtil() {}

    // Static block to initialize the DataSource
    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/MySQLDB");
        } catch (NamingException e) {
            throw new RuntimeException("Failed to initialize DataSource", e);
        }
    }

    // Singleton instance getter
    public static DataSource getDataSource() {
        return dataSource;
    }

    // Method to get a connection from the DataSource
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get database connection", e);
        }
    }
}
