package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.constant.VehicleStatus;
import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.dto.VehicleDriverDTO;
import com.project.megacitycab.util.DBUtil;
import com.project.megacitycab.util.exception.MegaCityCabException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VehicleDriverServiceImplTest {

    private VehicleDriverServiceImpl vehicleDriverService;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        vehicleDriverService = new VehicleDriverServiceImpl();
        connection = DBUtil.getConnection();
        // Clean the vehicles and drivers tables before each test
        connection.prepareStatement("DELETE FROM vehicles").executeUpdate();
        connection.prepareStatement("DELETE FROM drivers").executeUpdate();
    }

    // Helper method to add a vehicle-driver pair and return its vehicle ID
    private String addVehicleDriverAndGetId(VehicleDriverDTO vehicleDriverDTO) throws MegaCityCabException, SQLException, ClassNotFoundException {
        vehicleDriverService.add(vehicleDriverDTO);
        VehicleDTO addedVehicle = vehicleDriverService.findVehicleByVehicleId(vehicleDriverService.getLastInsertedId(connection));
        if (addedVehicle == null || addedVehicle.getId() == null) {
            throw new RuntimeException("Failed to retrieve added vehicle ID");
        }
        return addedVehicle.getId();
    }

    @Test
    void add() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Test successful addition
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL001")
                .name("Test Driver")
                .mobileNo("0771234567")
                .email("driver@example.com")
                .experience(5)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("ABC123")
                .model("Camry")
                .brand("Toyota")
                .capacity(4)
                .color("Black")
                .pricePerKm(100.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();

        boolean result = vehicleDriverService.add(vehicleDriverDTO);
        assertTrue(result, "Vehicle and driver should be added successfully");

        // Verify addition
        String vehicleId = vehicleDriverService.getLastInsertedId(connection);
        VehicleDriverDTO addedVehicleDriver = vehicleDriverService.searchById(vehicleId);
        assertNotNull(addedVehicleDriver, "Added vehicle-driver should exist");
        assertEquals("ABC123", addedVehicleDriver.getVehicle().getLicensePlate(), "License plate should match");
        assertEquals("DL001", addedVehicleDriver.getDriver().getLicenseNo(), "Driver license should match");
    }

    @Test
    void update() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair first
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL002")
                .name("Test Driver")
                .mobileNo("0771234567")
                .email("driver@example.com")
                .experience(5)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("XYZ789")
                .model("Civic")
                .brand("Honda")
                .capacity(4)
                .color("White")
                .pricePerKm(120.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        String vehicleId = addVehicleDriverAndGetId(vehicleDriverDTO);
        DriverDTO addedDriver = vehicleDriverService.findDriverByVehicleId(vehicleId);

        // Test successful update
        VehicleDTO updatedVehicle = new VehicleDTO.VehicleDTOBuilder()
                .id(vehicleId)
                .licensePlate("XYZ789")
                .driverId(addedDriver.getId())
                .model("Accord")
                .brand("Honda")
                .capacity(5)
                .color("Blue")
                .pricePerKm(150.0)
                .status(VehicleStatus.available)
                .build();
        DriverDTO updatedDriver = new DriverDTO.DriverDTOBuilder()
                .id(addedDriver.getId())
                .licenseNo("DL002")
                .name("Updated Driver")
                .mobileNo("0779876543")
                .email("updated@example.com")
                .experience(6)
                .build();
        VehicleDriverDTO updatedVehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .vehicle(updatedVehicle)
                .driver(updatedDriver)
                .build();

        boolean result = vehicleDriverService.update(updatedVehicleDriverDTO);
        assertTrue(result, "Vehicle and driver should be updated successfully");

        // Verify update
        VehicleDriverDTO fetchedVehicleDriver = vehicleDriverService.searchById(vehicleId);
        assertEquals("Updated Driver", fetchedVehicleDriver.getDriver().getName(), "Driver name should be updated");
        assertEquals("Blue", fetchedVehicleDriver.getVehicle().getColor(), "Vehicle color should be updated");
    }

    @Test
    void delete() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair first
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL003")
                .name("Delete Driver")
                .mobileNo("0771234567")
                .email("delete@example.com")
                .experience(3)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("DEF456")
                .model("Prius")
                .brand("Toyota")
                .capacity(4)
                .color("Silver")
                .pricePerKm(110.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        String vehicleId = addVehicleDriverAndGetId(vehicleDriverDTO);
        DriverDTO addedDriver = vehicleDriverService.findDriverByVehicleId(vehicleId);

        // Test successful deletion
        boolean result = vehicleDriverService.delete(vehicleId, addedDriver.getId());
        assertTrue(result, "Vehicle and driver should be deleted successfully");

        // Verify deletion
        VehicleDriverDTO deletedVehicleDriver = vehicleDriverService.searchById(vehicleId);
        assertNull(deletedVehicleDriver, "Deleted vehicle-driver should not exist");
    }

    @Test
    void searchById() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair first
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL004")
                .name("Search Driver")
                .mobileNo("0771234567")
                .email("search@example.com")
                .experience(4)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("GHI789")
                .model("Model 3")
                .brand("Tesla")
                .capacity(4)
                .color("Red")
                .pricePerKm(200.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        String vehicleId = addVehicleDriverAndGetId(vehicleDriverDTO);

        // Test successful search
        VehicleDriverDTO foundVehicleDriver = vehicleDriverService.searchById(vehicleId);
        assertNotNull(foundVehicleDriver, "Vehicle-driver should be found by ID");
        assertEquals("GHI789", foundVehicleDriver.getVehicle().getLicensePlate(), "License plate should match");
        assertEquals("Search Driver", foundVehicleDriver.getDriver().getName(), "Driver name should match");

        // Test non-existent ID
        VehicleDriverDTO notFound = vehicleDriverService.searchById("V999");
        assertNull(notFound, "Non-existent vehicle-driver should return null");
    }

    @Test
    void getAll() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add multiple vehicle-driver pairs
        DriverDTO driver1 = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL005")
                .name("Driver One")
                .mobileNo("0771234567")
                .email("one@example.com")
                .experience(5)
                .build();
        VehicleDTO vehicle1 = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("JKL012")
                .model("Corolla")
                .brand("Toyota")
                .capacity(4)
                .color("Green")
                .pricePerKm(90.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vd1 = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver1)
                .vehicle(vehicle1)
                .build();

        DriverDTO driver2 = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL006")
                .name("Driver Two")
                .mobileNo("0779876543")
                .email("two@example.com")
                .experience(6)
                .build();
        VehicleDTO vehicle2 = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("MNO345")
                .model("RAV4")
                .brand("Toyota")
                .capacity(5)
                .color("Blue")
                .pricePerKm(130.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vd2 = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver2)
                .vehicle(vehicle2)
                .build();

        vehicleDriverService.add(vd1);
        vehicleDriverService.add(vd2);

        // Test getAll without search params
        List<VehicleDriverDTO> allVehicleDrivers = vehicleDriverService.getAll(null);
        assertEquals(2, allVehicleDrivers.size(), "Should return all vehicle-drivers");

        // Test getAll with search params
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("brand", "Toyota");
        List<VehicleDriverDTO> filteredVehicleDrivers = vehicleDriverService.getAll(searchParams);
        assertEquals(2, filteredVehicleDrivers.size(), "Should return all Toyota vehicles");
    }

    @Test
    void existByPk() {
        // This method is not implemented in VehicleDriverServiceImpl (returns false by default)
        assertFalse(vehicleDriverService.existByPk("V001"), "existByPk should return false as it's not implemented");
    }

    @Test
    void existByLicensePlate() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL007")
                .name("License Driver")
                .mobileNo("0771234567")
                .email("license@example.com")
                .experience(3)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("PQR678")
                .model("Yaris")
                .brand("Toyota")
                .capacity(4)
                .color("Yellow")
                .pricePerKm(95.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        vehicleDriverService.add(vehicleDriverDTO);

        // Test existing license plate
        boolean exists = vehicleDriverService.existByLicensePlate("PQR678");
        assertTrue(exists, "Vehicle should exist by license plate");

        // Test non-existent license plate
        boolean notExists = vehicleDriverService.existByLicensePlate("XYZ999");
        assertFalse(notExists, "Non-existent license plate should not exist");
    }

    @Test
    void existsByVehicleId() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL008")
                .name("Vehicle Id Driver")
                .mobileNo("0771234567")
                .email("vid@example.com")
                .experience(4)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("STU901")
                .model("Focus")
                .brand("Ford")
                .capacity(4)
                .color("Grey")
                .pricePerKm(110.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        String vehicleId = addVehicleDriverAndGetId(vehicleDriverDTO);

        // Test existing vehicle ID
        boolean exists = vehicleDriverService.existsByVehicleId(vehicleId);
        assertTrue(exists, "Vehicle should exist by ID");

        // Test non-existent vehicle ID
        boolean notExists = vehicleDriverService.existsByVehicleId("V999");
        assertFalse(notExists, "Non-existent vehicle ID should not exist");
    }

    @Test
    void existsByDriverId() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL009")
                .name("Driver Id Driver")
                .mobileNo("0771234567")
                .email("did@example.com")
                .experience(5)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("VWX234")
                .model("Escape")
                .brand("Ford")
                .capacity(5)
                .color("Black")
                .pricePerKm(140.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        String vehicleId = addVehicleDriverAndGetId(vehicleDriverDTO);
        DriverDTO addedDriver = vehicleDriverService.findDriverByVehicleId(vehicleId);

        // Test existing driver ID
        boolean exists = vehicleDriverService.existsByDriverId(addedDriver.getId());
        assertTrue(exists, "Driver should exist by ID");

        // Test non-existent driver ID
        boolean notExists = vehicleDriverService.existsByDriverId("D999");
        assertFalse(notExists, "Non-existent driver ID should not exist");
    }

    @Test
    void existsByDriverLicense() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL010")
                .name("License Check Driver")
                .mobileNo("0771234567")
                .email("licensecheck@example.com")
                .experience(6)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("YZA567")
                .model("Outlander")
                .brand("Mitsubishi")
                .capacity(5)
                .color("Silver")
                .pricePerKm(150.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        vehicleDriverService.add(vehicleDriverDTO);

        // Test existing driver license
        boolean exists = vehicleDriverService.existsByDriverLicense("DL010");
        assertTrue(exists, "Driver should exist by license");

        // Test non-existent driver license
        boolean notExists = vehicleDriverService.existsByDriverLicense("DL999");
        assertFalse(notExists, "Non-existent driver license should not exist");
    }

    @Test
    void getAllVehicles() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add multiple vehicle-driver pairs
        DriverDTO driver1 = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL011")
                .name("Vehicle Driver 1")
                .mobileNo("0771234567")
                .email("v1@example.com")
                .experience(4)
                .build();
        VehicleDTO vehicle1 = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("BCD890")
                .model("CR-V")
                .brand("Honda")
                .capacity(5)
                .color("Red")
                .pricePerKm(130.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vd1 = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver1)
                .vehicle(vehicle1)
                .build();

        DriverDTO driver2 = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL012")
                .name("Vehicle Driver 2")
                .mobileNo("0779876543")
                .email("v2@example.com")
                .experience(5)
                .build();
        VehicleDTO vehicle2 = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("EFG123")
                .model("X5")
                .brand("BMW")
                .capacity(5)
                .color("Black")
                .pricePerKm(200.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vd2 = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver2)
                .vehicle(vehicle2)
                .build();

        vehicleDriverService.add(vd1);
        vehicleDriverService.add(vd2);

        // Test getAllVehicles without search params
        List<VehicleDTO> allVehicles = vehicleDriverService.getAllVehicles(null);
        assertEquals(2, allVehicles.size(), "Should return all vehicles");

        // Test with search params
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("brand", "Honda");
        List<VehicleDTO> filteredVehicles = vehicleDriverService.getAllVehicles(searchParams);
        assertEquals(1, filteredVehicles.size(), "Should return only Honda vehicles");
        assertEquals("CR-V", filteredVehicles.get(0).getModel(), "Model should match");
    }

    @Test
    void findDriverByVehicleId() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL013")
                .name("Find Driver")
                .mobileNo("0771234567")
                .email("find@example.com")
                .experience(5)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("HIJ456")
                .model("Pilot")
                .brand("Honda")
                .capacity(7)
                .color("White")
                .pricePerKm(160.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        String vehicleId = addVehicleDriverAndGetId(vehicleDriverDTO);

        // Test finding driver
        DriverDTO foundDriver = vehicleDriverService.findDriverByVehicleId(vehicleId);
        assertNotNull(foundDriver, "Driver should be found by vehicle ID");
        assertEquals("DL013", foundDriver.getLicenseNo(), "License number should match");

        // Test non-existent vehicle ID
        DriverDTO notFound = vehicleDriverService.findDriverByVehicleId("V999");
        assertNull(notFound, "Non-existent vehicle ID should return null");
    }

    @Test
    void findVehicleByVehicleId() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL014")
                .name("Vehicle Finder")
                .mobileNo("0771234567")
                .email("vfinder@example.com")
                .experience(4)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("KLM789")
                .model("Forester")
                .brand("Subaru")
                .capacity(5)
                .color("Green")
                .pricePerKm(140.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        String vehicleId = addVehicleDriverAndGetId(vehicleDriverDTO);

        // Test finding vehicle
        VehicleDTO foundVehicle = vehicleDriverService.findVehicleByVehicleId(vehicleId);
        assertNotNull(foundVehicle, "Vehicle should be found by ID");
        assertEquals("KLM789", foundVehicle.getLicensePlate(), "License plate should match");

        // Test non-existent vehicle ID
        VehicleDTO notFound = vehicleDriverService.findVehicleByVehicleId("V999");
        assertNull(notFound, "Non-existent vehicle ID should return null");
    }

    @Test
    void findDriverByDriverId() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL015")
                .name("Driver Finder")
                .mobileNo("0771234567")
                .email("dfinder@example.com")
                .experience(6)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("NOP012")
                .model("Tucson")
                .brand("Hyundai")
                .capacity(5)
                .color("Blue")
                .pricePerKm(135.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        String vehicleId = addVehicleDriverAndGetId(vehicleDriverDTO);
        DriverDTO addedDriver = vehicleDriverService.findDriverByVehicleId(vehicleId);

        // Test finding driver
        DriverDTO foundDriver = vehicleDriverService.findDriverByDriverId(addedDriver.getId());
        assertNotNull(foundDriver, "Driver should be found by ID");
        assertEquals("DL015", foundDriver.getLicenseNo(), "License number should match");

        // Test non-existent driver ID
        DriverDTO notFound = vehicleDriverService.findDriverByDriverId("D999");
        assertNull(notFound, "Non-existent driver ID should return null");
    }

    @Test
    void getLastInsertedId() throws MegaCityCabException, SQLException, ClassNotFoundException {
        // Add a vehicle-driver pair
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL017")
                .name("Last Id Driver")
                .mobileNo("0771234567")
                .email("lastid@example.com")
                .experience(5)
                .build();
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("TUV678")
                .model("Rogue")
                .brand("Nissan")
                .capacity(5)
                .color("Silver")
                .pricePerKm(125.0)
                .status(VehicleStatus.available)
                .build();
        VehicleDriverDTO vehicleDriverDTO = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                .driver(driver)
                .vehicle(vehicle)
                .build();
        vehicleDriverService.add(vehicleDriverDTO);

        // Test getLastInsertedId
        String lastId = vehicleDriverService.getLastInsertedId(connection);
        assertNotNull(lastId, "Last inserted ID should not be null");

        // Verify the ID corresponds to the added vehicle
        VehicleDTO fetchedVehicle = vehicleDriverService.findVehicleByVehicleId(lastId);
        assertNotNull(fetchedVehicle, "Vehicle with last inserted ID should exist");
        assertEquals("TUV678", fetchedVehicle.getLicensePlate(), "License plate should match");
    }
}
