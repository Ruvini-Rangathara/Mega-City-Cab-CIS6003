package com.project.megacitycab.util;

import com.project.megacitycab.util.exception.MegaCityCabException;
import com.project.megacitycab.util.exception.MegaCityCabExceptionType;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil {
    private static final Logger logger = Logger.getLogger(DBUtil.class.getName());
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static String DRIVER;

    static {
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                logger.log(Level.SEVERE, "Unable to find db.properties");
                throw new MegaCityCabException(MegaCityCabExceptionType.FAILED_TO_INITIALIZE_DATASOURCE);
            }
            prop.load(input);
            URL = prop.getProperty("db.url");
            USERNAME = prop.getProperty("db.username");
            PASSWORD = prop.getProperty("db.password");
            DRIVER = prop.getProperty("db.driver");

            // Register the JDBC driver
            Class.forName(DRIVER);
            logger.log(Level.INFO, "Database driver initialized successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize database connection properties", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.FAILED_TO_INITIALIZE_DATASOURCE);
        }
    }

    private DBUtil() {
    }

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.log(Level.FINE, "Database connection obtained successfully");
            return conn;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get database connection", e);
            throw new RuntimeException("Failed to get database connection", e);
        }
    }

    // Optional: Add a close method if you want to keep it consistent with previous usage
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                logger.log(Level.FINE, "Database connection closed successfully");
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Failed to close database connection", e);
            }
        }
    }
}
