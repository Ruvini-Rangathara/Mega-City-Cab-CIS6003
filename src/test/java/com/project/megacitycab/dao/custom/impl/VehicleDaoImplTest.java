package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.constant.VehicleStatus;
import com.project.megacitycab.entity.Driver;
import com.project.megacitycab.entity.Vehicle;
import com.project.megacitycab.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleDaoImplTest {

    private VehicleDaoImpl vehicleDao;
    private DriverDaoImpl driverDao;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        vehicleDao = new VehicleDaoImpl();
        driverDao = new DriverDaoImpl();
        connection = DBUtil.getConnection();
        // Ensure relevant tables are in a clean state before each test
        connection.prepareStatement("DELETE FROM vehicles").executeUpdate();
        connection.prepareStatement("DELETE FROM drivers").executeUpdate();
        // Clear bookings table if it exists
        try {
            connection.prepareStatement("DELETE FROM bookings").executeUpdate();
        } catch (SQLException e) {
            System.out.println("No bookings table found or not needed: " + e.getMessage());
        }

        // Add a default driver for use in tests
        Driver driver = new Driver.DriverBuilder().name("Test Driver").licenseNo("LIC000").mobileNo("0000000000").experience(5).email("test.driver@example.com").build();
        driverDao.add(connection, driver);
    }

    // Helper method to get the last inserted driver ID
    private String getDriverId() throws SQLException, ClassNotFoundException {
        return driverDao.getLastInsertedId(connection);
    }

    // Helper method to find vehicle by licensePlate
    private Vehicle findVehicleByLicensePlate(String licensePlate) throws SQLException, ClassNotFoundException {
        List<Vehicle> vehicles = vehicleDao.getAll(connection, null);
        return vehicles.stream().filter(v -> v.getLicensePlate().equals(licensePlate)).findFirst().orElse(null);
    }

    @Test
    void add() throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle.VehicleBuilder().licensePlate("ABC123").driverId(getDriverId()).model("Camry").brand("Toyota").capacity(4).color("Blue").pricePerKm(2.5).status(VehicleStatus.available).build();

        boolean result = vehicleDao.add(connection, vehicle);
        assertTrue(result, "Vehicle should be added successfully");

        Vehicle savedVehicle = findVehicleByLicensePlate("ABC123");
        assertNotNull(savedVehicle, "Vehicle should not be null");
        assertEquals("Camry", savedVehicle.getModel(), "Model should match");
        assertEquals("Toyota", savedVehicle.getBrand(), "Brand should match");
    }

    @Test
    void update() throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle.VehicleBuilder().licensePlate("DEF456").driverId(getDriverId()).model("Civic").brand("Honda").capacity(5).color("Red").pricePerKm(3.0).status(VehicleStatus.available).build();
        vehicleDao.add(connection, vehicle);

        Vehicle addedVehicle = findVehicleByLicensePlate("DEF456");
        assertNotNull(addedVehicle, "Vehicle should exist after being added");

        Vehicle updatedVehicle = new Vehicle.VehicleBuilder().id(addedVehicle.getId()).licensePlate("DEF456").driverId(getDriverId()).model("Accord").brand("Honda").capacity(5).color("Black").pricePerKm(3.5).status(VehicleStatus.available).build();

        boolean result = vehicleDao.update(connection, updatedVehicle);
        assertTrue(result, "Vehicle should be updated successfully");

        Vehicle fetchedVehicle = vehicleDao.searchById(connection, addedVehicle.getId());
        assertNotNull(fetchedVehicle, "Vehicle should not be null");
        assertEquals("Accord", fetchedVehicle.getModel(), "Model should be updated");
        assertEquals("Black", fetchedVehicle.getColor(), "Color should be updated");
        assertEquals(VehicleStatus.available, fetchedVehicle.getStatus(), "Status should be updated");
    }

    @Test
    void delete() throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle.VehicleBuilder().licensePlate("GHI789").driverId(getDriverId()).model("Corolla").brand("Toyota").capacity(4).color("White").pricePerKm(2.0).status(VehicleStatus.available).build();
        vehicleDao.add(connection, vehicle);

        Vehicle addedVehicle = findVehicleByLicensePlate("GHI789");
        assertNotNull(addedVehicle, "Vehicle should exist after being added");

        boolean result = vehicleDao.delete(connection, addedVehicle.getId());
        assertTrue(result, "Vehicle should be soft-deleted successfully");

        Vehicle deletedVehicle = vehicleDao.searchById(connection, addedVehicle.getId());
        assertNull(deletedVehicle, "Vehicle should not be returned after soft delete");
    }

    @Test
    void searchById() throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle.VehicleBuilder().licensePlate("JKL012").driverId(getDriverId()).model("Prius").brand("Toyota").capacity(4).color("Silver").pricePerKm(2.8).status(VehicleStatus.available).build();
        vehicleDao.add(connection, vehicle);

        Vehicle addedVehicle = findVehicleByLicensePlate("JKL012");
        assertNotNull(addedVehicle, "Vehicle should exist after being added");

        Vehicle foundVehicle = vehicleDao.searchById(connection, addedVehicle.getId());
        assertNotNull(foundVehicle, "Vehicle should not be null");
        assertEquals("Prius", foundVehicle.getModel(), "Model should match");
        assertEquals("Silver", foundVehicle.getColor(), "Color should match");
    }

    @Test
    void getAll() throws SQLException, ClassNotFoundException {
        Vehicle vehicle1 = new Vehicle.VehicleBuilder().licensePlate("MNO345").driverId(getDriverId()).model("Model 3").brand("Tesla").capacity(5).color("Black").pricePerKm(4.0).status(VehicleStatus.available).build();
        Vehicle vehicle2 = new Vehicle.VehicleBuilder().licensePlate("PQR678").driverId(getDriverId()).model("Leaf").brand("Nissan").capacity(4).color("Green").pricePerKm(3.2).status(VehicleStatus.available).build();
        vehicleDao.add(connection, vehicle1);
        vehicleDao.add(connection, vehicle2);

        List<Vehicle> vehicles = vehicleDao.getAll(connection, null);
        assertTrue(vehicles.size() >= 2, "At least 2 vehicles should exist");
    }

    @Test
    void existByPk() throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle.VehicleBuilder().licensePlate("STU901").driverId(getDriverId()).model("Outlander").brand("Mitsubishi").capacity(6).color("Grey").pricePerKm(3.8).status(VehicleStatus.available).build();
        vehicleDao.add(connection, vehicle);

        Vehicle addedVehicle = findVehicleByLicensePlate("STU901");
        assertNotNull(addedVehicle, "Vehicle should exist after being added");

        boolean exists = vehicleDao.existByPk(connection, addedVehicle.getId());
        assertTrue(exists, "Vehicle should exist with given PK");

        boolean notExists = vehicleDao.existByPk(connection, "non-existent-id");
        assertFalse(notExists, "Vehicle should not exist with non-existent ID");
    }

    @Test
    void existByLicensePlate() throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle.VehicleBuilder().licensePlate("VWX234").driverId(getDriverId()).model("RAV4").brand("Toyota").capacity(5).color("Blue").pricePerKm(3.0).status(VehicleStatus.available).build();
        vehicleDao.add(connection, vehicle);

        boolean exists = vehicleDao.existByLicensePlate(connection, "VWX234");
        assertTrue(exists, "Vehicle should exist with given license plate");

        boolean notExists = vehicleDao.existByLicensePlate(connection, "NONEXISTENT");
        assertFalse(notExists, "Vehicle should not exist with non-existent license plate");
    }

    @Test
    void getAvailableVehicles() throws SQLException, ClassNotFoundException {

        // Create and add multiple vehicles
        Vehicle vehicle1 = new Vehicle.VehicleBuilder().licensePlate("MNO345").driverId(getDriverId()).model("Model 3").brand("Tesla").capacity(5).color("Black").pricePerKm(4.0).status(VehicleStatus.available).build();

        Vehicle vehicle2 = new Vehicle.VehicleBuilder().licensePlate("PQR678").driverId(getDriverId()).model("Leaf").brand("Nissan").capacity(4).color("Green").pricePerKm(3.2).status(VehicleStatus.available).build();

        // Add vehicles to the database
        assertTrue(vehicleDao.add(connection, vehicle1), "First vehicle should be added successfully");
        assertTrue(vehicleDao.add(connection, vehicle2), "Second vehicle should be added successfully");

        // Get all vehicles
        List<Vehicle> vehicles = vehicleDao.getAll(connection, null);

        // Verify list is not null and contains the expected number of vehicles
        assertNotNull(vehicles, "Vehicles list should not be null");
        assertEquals(2, vehicles.size(), "There should be exactly 2 vehicles");

        // Verify the vehicles in the list match the added vehicles
        boolean foundVehicle1 = false;
        boolean foundVehicle2 = false;

        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equals("MNO345")) {
                foundVehicle1 = true;
                assertEquals("Tesla", v.getBrand(), "Vehicle 1 brand should match");
                assertEquals("Model 3", v.getModel(), "Vehicle 1 model should match");
                assertEquals(VehicleStatus.available, v.getStatus(), "Vehicle 1 status should match");
            }

            if (v.getLicensePlate().equals("PQR678")) {
                foundVehicle2 = true;
                assertEquals("Nissan", v.getBrand(), "Vehicle 2 brand should match");
                assertEquals("Leaf", v.getModel(), "Vehicle 2 model should match");
                assertEquals(VehicleStatus.available, v.getStatus(), "Vehicle 2 status should match");
            }
        }

        assertTrue(foundVehicle1, "Vehicle 1 should be in the list");
        assertTrue(foundVehicle2, "Vehicle 2 should be in the list");
    }
}
