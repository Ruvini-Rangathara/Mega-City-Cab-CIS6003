package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.entity.Driver;
import com.project.megacitycab.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DriverDaoImplTest {

    private DriverDaoImpl driverDao;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        driverDao = new DriverDaoImpl();
        connection = DBUtil.getConnection();
        // Ensure the database is in a clean state before each test
        connection.prepareStatement("DELETE FROM drivers").executeUpdate();
    }

    @Test
    void add() throws SQLException, ClassNotFoundException {
        Driver driver = new Driver.DriverBuilder()
                .name("John Driver")
                .licenseNo("LIC123")
                .mobileNo("1234567890")
                .experience(5)
                .email("john.driver@example.com")
                .build();

        boolean result = driverDao.add(connection, driver);
        assertTrue(result, "Driver should be added successfully");

        // Verify the driver was actually added
        String lastId = driverDao.getLastInsertedId(connection);
        Driver savedDriver = driverDao.searchById(connection, lastId);
        assertNotNull(savedDriver, "Driver should not be null");
        assertEquals("John Driver", savedDriver.getName(), "Name should match");
        assertEquals("LIC123", savedDriver.getLicenseNo(), "License number should match");
    }

    @Test
    void update() throws SQLException, ClassNotFoundException {
        // First add a driver
        Driver driver = new Driver.DriverBuilder()
                .name("Jane Driver")
                .licenseNo("LIC456")
                .mobileNo("0987654321")
                .experience(3)
                .email("jane.driver@example.com")
                .build();
        driverDao.add(connection, driver);

        // Retrieve the added driver to get the ID
        String lastId = driverDao.getLastInsertedId(connection);
        Driver addedDriver = driverDao.searchById(connection, lastId);
        assertNotNull(addedDriver, "Driver should exist after being added");

        // Update the driver
        Driver updatedDriver = new Driver.DriverBuilder()
                .id(addedDriver.getId())
                .name("Jane Smith")
                .licenseNo("LIC456")
                .mobileNo("1112223333")
                .experience(4)
                .email("jane.smith@example.com")
                .build();

        boolean result = driverDao.update(connection, updatedDriver);
        assertTrue(result, "Driver should be updated successfully");

        // Verify update
        Driver fetchedDriver = driverDao.searchById(connection, addedDriver.getId());
        assertNotNull(fetchedDriver, "Driver should not be null");
        assertEquals("Jane Smith", fetchedDriver.getName(), "Name should be updated");
        assertEquals(4, fetchedDriver.getExperience(), "Experience should be updated");
    }

    @Test
    void delete() throws SQLException, ClassNotFoundException {
        // Add a driver first
        Driver driver = new Driver.DriverBuilder()
                .name("Delete Driver")
                .licenseNo("LIC789")
                .mobileNo("9999999999")
                .experience(2)
                .email("delete.driver@example.com")
                .build();
        driverDao.add(connection, driver);

        // Retrieve the added driver to get the ID
        String lastId = driverDao.getLastInsertedId(connection);
        Driver addedDriver = driverDao.searchById(connection, lastId);
        assertNotNull(addedDriver, "Driver should exist after being added");

        boolean result = driverDao.delete(connection, addedDriver.getId());
        assertTrue(result, "Driver should be soft-deleted successfully");

        // Verify soft delete
        Driver deletedDriver = driverDao.searchById(connection, addedDriver.getId());
        assertNull(deletedDriver, "Driver should not be returned after soft delete");
    }

    @Test
    void searchById() throws SQLException, ClassNotFoundException {
        Driver driver = new Driver.DriverBuilder()
                .name("Search Driver")
                .licenseNo("LIC101")
                .mobileNo("1010101010")
                .experience(6)
                .email("search.driver@example.com")
                .build();
        driverDao.add(connection, driver);

        // Retrieve the added driver to get the ID
        String lastId = driverDao.getLastInsertedId(connection);
        Driver addedDriver = driverDao.searchById(connection, lastId);
        assertNotNull(addedDriver, "Driver should exist after being added");

        Driver foundDriver = driverDao.searchById(connection, addedDriver.getId());
        assertNotNull(foundDriver, "Driver should not be null");
        assertEquals("Search Driver", foundDriver.getName(), "Name should match");
        assertEquals("search.driver@example.com", foundDriver.getEmail(), "Email should match");
    }

    @Test
    void getAll() throws SQLException, ClassNotFoundException {
        // Add some test drivers
        Driver driver1 = new Driver.DriverBuilder()
                .name("Driver1")
                .licenseNo("LIC111")
                .mobileNo("1111111111")
                .experience(1)
                .email("driver1@example.com")
                .build();
        Driver driver2 = new Driver.DriverBuilder()
                .name("Driver2")
                .licenseNo("LIC222")
                .mobileNo("2222222222")
                .experience(2)
                .email("driver2@example.com")
                .build();
        driverDao.add(connection, driver1);
        driverDao.add(connection, driver2);

        List<Driver> drivers = driverDao.getAll(connection, null);
        assertTrue(drivers.size() >= 2, "At least 2 drivers should exist");
    }

    @Test
    void existByPk() throws SQLException, ClassNotFoundException {
        Driver driver = new Driver.DriverBuilder()
                .name("Exist Driver")
                .licenseNo("LIC333")
                .mobileNo("3333333333")
                .experience(3)
                .email("exist.driver@example.com")
                .build();
        driverDao.add(connection, driver);

        // Retrieve the added driver to get the ID
        String lastId = driverDao.getLastInsertedId(connection);
        Driver addedDriver = driverDao.searchById(connection, lastId);
        assertNotNull(addedDriver, "Driver should exist after being added");

        boolean exists = driverDao.existByPk(connection, addedDriver.getId());
        assertTrue(exists, "Driver should exist with given PK");

        boolean notExists = driverDao.existByPk(connection, "non-existent-id");
        assertFalse(notExists, "Driver should not exist with non-existent ID");
    }

    @Test
    void existByLicenseNo() throws SQLException, ClassNotFoundException {
        Driver driver = new Driver.DriverBuilder()
                .name("License Driver")
                .licenseNo("LIC444")
                .mobileNo("4444444444")
                .experience(4)
                .email("license.driver@example.com")
                .build();
        driverDao.add(connection, driver);

        boolean exists = driverDao.existByLicenseNo(connection, "LIC444");
        assertTrue(exists, "Driver should exist with given license number");

        boolean notExists = driverDao.existByLicenseNo(connection, "NONEXISTENT");
        assertFalse(notExists, "Driver should not exist with non-existent license number");
    }

    @Test
    void getLastInsertedId() throws SQLException, ClassNotFoundException {
        // Add a driver
        Driver driver = new Driver.DriverBuilder()
                .name("Last Driver")
                .licenseNo("LIC555")
                .mobileNo("5555555555")
                .experience(5)
                .email("last.driver@example.com")
                .build();
        driverDao.add(connection, driver);

        // Get the last inserted ID
        String lastId = driverDao.getLastInsertedId(connection);
        assertNotNull(lastId, "Last inserted ID should not be null");

        // Verify the driver exists with that ID
        Driver fetchedDriver = driverDao.searchById(connection, lastId);
        assertNotNull(fetchedDriver, "Driver with last inserted ID should exist");
        assertEquals("Last Driver", fetchedDriver.getName(), "Name should match");
        assertEquals("LIC555", fetchedDriver.getLicenseNo(), "License number should match");
    }
}
