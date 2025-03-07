package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.constant.BookingStatus;
import com.project.megacitycab.constant.VehicleStatus;
import com.project.megacitycab.entity.Booking;
import com.project.megacitycab.entity.Customer;
import com.project.megacitycab.entity.Driver;
import com.project.megacitycab.entity.Vehicle;
import com.project.megacitycab.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookingDaoImplTest {

    private BookingDaoImpl bookingDao;
    private VehicleDaoImpl vehicleDao;
    private DriverDaoImpl driverDao;
    private CustomerDaoImpl customerDao;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        bookingDao = new BookingDaoImpl();
        vehicleDao = new VehicleDaoImpl();
        driverDao = new DriverDaoImpl();
        customerDao = new CustomerDaoImpl();
        connection = DBUtil.getConnection();

        // Disable auto-commit to manage transactions manually
        connection.setAutoCommit(false);

        // Clean the database before each test
        connection.prepareStatement("DELETE FROM bookings").executeUpdate();
        connection.prepareStatement("DELETE FROM vehicles").executeUpdate();
        connection.prepareStatement("DELETE FROM drivers").executeUpdate();
        connection.prepareStatement("DELETE FROM customers").executeUpdate();

        // Add a default driver
        Driver driver = new Driver.DriverBuilder()
                .name("Test Driver")
                .licenseNo("LIC000")
                .mobileNo("0000000000")
                .experience(5)
                .email("test.driver@example.com")
                .build();
        driverDao.add(connection, driver);
        connection.commit();

        // Add a default vehicle
        Vehicle vehicle = new Vehicle.VehicleBuilder()
                .licensePlate("ABC123")
                .driverId(getDriverId())
                .model("Camry")
                .brand("Toyota")
                .capacity(4)
                .color("Blue")
                .pricePerKm(2.5)
                .status(VehicleStatus.available)
                .build();
        vehicleDao.add(connection, vehicle);
        connection.commit();

        // Add a default customer
        Customer customer = new Customer.CustomerBuilder()
                .id(UUID.randomUUID().toString())
                .registrationNo("REG001")
                .name("Test Customer")
                .address("123 Test St")
                .nic("NIC001")
                .dob(new Date())
                .mobileNo("1234567890")
                .email("test.customer@example.com")
                .build();
        customerDao.add(connection, customer);
        connection.commit();
    }

    // Helper method to get the last inserted driver ID
    private String getDriverId() throws SQLException, ClassNotFoundException {
        String id = driverDao.getLastInsertedId(connection);
        if (id == null) {
            throw new RuntimeException("Failed to retrieve driver ID");
        }
        return id;
    }

    // Helper method to get the last inserted vehicle ID
    private String getVehicleId() throws SQLException, ClassNotFoundException {
        List<Vehicle> vehicles = vehicleDao.getAll(connection, null);
        if (vehicles.isEmpty()) {
            throw new RuntimeException("No vehicles found");
        }
        return vehicles.get(0).getId();
    }

    // Helper method to get the last inserted customer ID
    private String getCustomerId() throws SQLException, ClassNotFoundException {
        List<Customer> customers = customerDao.getAll(connection, null);
        if (customers.isEmpty()) {
            throw new RuntimeException("No customers found");
        }
        return customers.get(0).getId();
    }

    // Helper method to get the last inserted booking ID
    private String getBookingId() throws SQLException, ClassNotFoundException {
        List<Booking> bookings = bookingDao.getAll(connection, null);
        if (bookings.isEmpty()) {
            throw new RuntimeException("No bookings found");
        }
        return bookings.get(0).getId();
    }

    // Helper method to find booking by ID
    private Booking findBookingById(String id) throws SQLException, ClassNotFoundException {
        return bookingDao.searchById(connection, id);
    }

    @Test
    void add() throws SQLException, ClassNotFoundException {
        Booking booking = new Booking.BookingBuilder()
                .customerId(getCustomerId())
                .bookingDate(LocalDate.now())
                .pickupLocation("Location A")
                .destination("Location B")
                .pickupTime(LocalTime.of(9, 0))
                .releaseTime(LocalTime.of(12, 0))
                .vehicleId(getVehicleId())
                .status(BookingStatus.pending)
                .distance(10.0)
                .fare(25.0)
                .discount(0.0)
                .tax(2.0)
                .netTotal(27.0)
                .userId(null)
                .build();

        boolean result = bookingDao.add(connection, booking);
        assertTrue(result, "Booking should be added successfully");

        connection.commit();

        String id = getBookingId();
        Booking savedBooking = bookingDao.searchById(connection, id);
        assertNotNull(savedBooking, "Booking should exist after being added");
        assertEquals(getCustomerId(), savedBooking.getCustomerId(), "Customer ID should match");
        assertEquals("Location A", savedBooking.getPickupLocation(), "Pickup location should match");
        assertEquals(BookingStatus.pending, savedBooking.getStatus(), "Status should match");
    }

    @Test
    void update() throws SQLException, ClassNotFoundException {
        Booking booking = new Booking.BookingBuilder()
                .customerId(getCustomerId())
                .bookingDate(LocalDate.now())
                .pickupLocation("Location C")
                .destination("Location D")
                .pickupTime(LocalTime.of(10, 0))
                .releaseTime(LocalTime.of(13, 0))
                .vehicleId(getVehicleId())
                .status(BookingStatus.pending)
                .distance(15.0)
                .fare(37.5)
                .discount(0.0)
                .tax(3.0)
                .netTotal(40.5)
                .userId(null)
                .createdAt(new Date())
                .build();
        bookingDao.add(connection, booking);
        connection.commit();

        String bookingId = getBookingId();
        Booking addedBooking = findBookingById(bookingId);
        assertNotNull(addedBooking, "Booking should exist after being added");

        Booking updatedBooking = new Booking.BookingBuilder()
                .id(bookingId)
                .customerId(getCustomerId())
                .bookingDate(LocalDate.now())
                .pickupLocation("Location E")
                .destination("Location F")
                .pickupTime(LocalTime.of(11, 0))
                .releaseTime(LocalTime.of(14, 0))
                .vehicleId(getVehicleId())
                .status(BookingStatus.confirmed)
                .distance(20.0)
                .fare(50.0)
                .discount(5.0)
                .tax(4.0)
                .netTotal(49.0)
                .userId(null)
                .build();

        boolean result = bookingDao.update(connection, updatedBooking);
        assertTrue(result, "Booking should be updated successfully");

        connection.commit();

        Booking fetchedBooking = findBookingById(bookingId);
        assertNotNull(fetchedBooking, "Updated booking should not be null");
        assertEquals("Location E", fetchedBooking.getPickupLocation(), "Pickup location should be updated");
        assertEquals(BookingStatus.confirmed, fetchedBooking.getStatus(), "Status should be updated");
        assertEquals(49.0, fetchedBooking.getNetTotal(), "Net total should be updated");
    }

    @Test
    void searchById() throws SQLException, ClassNotFoundException {
        Booking booking = new Booking.BookingBuilder()
                .customerId(getCustomerId())
                .bookingDate(LocalDate.now())
                .pickupLocation("Location I")
                .destination("Location J")
                .pickupTime(LocalTime.of(8, 0))
                .releaseTime(LocalTime.of(11, 0))
                .vehicleId(getVehicleId())
                .status(BookingStatus.pending)
                .distance(12.0)
                .fare(30.0)
                .discount(0.0)
                .tax(2.5)
                .netTotal(32.5)
                .userId(null)
                .createdAt(new Date())
                .build();
        bookingDao.add(connection, booking);
        connection.commit();

        String bookingId = getBookingId();
        Booking foundBooking = bookingDao.searchById(connection, bookingId);
        assertNotNull(foundBooking, "Booking should be found by ID");
        assertEquals("Location I", foundBooking.getPickupLocation(), "Pickup location should match");
        assertEquals(32.5, foundBooking.getNetTotal(), "Net total should match");
    }

    @Test
    void getAll() throws SQLException, ClassNotFoundException {
        Booking booking1 = new Booking.BookingBuilder()
                .customerId(getCustomerId())
                .bookingDate(LocalDate.now())
                .pickupLocation("Location K")
                .destination("Location L")
                .pickupTime(LocalTime.of(9, 0))
                .releaseTime(LocalTime.of(12, 0))
                .vehicleId(getVehicleId())
                .status(BookingStatus.pending)
                .distance(10.0)
                .fare(25.0)
                .discount(0.0)
                .tax(2.0)
                .netTotal(27.0)
                .userId(null)
                .createdAt(new Date())
                .build();

        Booking booking2 = new Booking.BookingBuilder()
                .customerId(getCustomerId())
                .bookingDate(LocalDate.now())
                .pickupLocation("Location M")
                .destination("Location N")
                .pickupTime(LocalTime.of(13, 0))
                .releaseTime(LocalTime.of(16, 0))
                .vehicleId(getVehicleId())
                .status(BookingStatus.confirmed)
                .distance(15.0)
                .fare(37.5)
                .discount(0.0)
                .tax(3.0)
                .netTotal(40.5)
                .userId(null)
                .createdAt(new Date())
                .build();

        bookingDao.add(connection, booking1);
        bookingDao.add(connection, booking2);

        connection.commit();

        List<Booking> bookings = bookingDao.getAll(connection, null);
        assertEquals(2, bookings.size(), "Should find 2 bookings");

        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("searchStatus", "confirmed");
        List<Booking> confirmedBookings = bookingDao.getAll(connection, searchParams);
        assertEquals(1, confirmedBookings.size(), "Should find 1 confirmed booking");
    }

    @Test
    void existByPk() throws SQLException, ClassNotFoundException {
        Booking booking = new Booking.BookingBuilder()
                .customerId(getCustomerId())
                .bookingDate(LocalDate.now())
                .pickupLocation("Location O")
                .destination("Location P")
                .pickupTime(LocalTime.of(14, 0))
                .releaseTime(LocalTime.of(17, 0))
                .vehicleId(getVehicleId())
                .status(BookingStatus.pending)
                .distance(5.0)
                .fare(12.5)
                .discount(0.0)
                .tax(1.0)
                .netTotal(13.5)
                .userId(null)
                .build();
        bookingDao.add(connection, booking);
        connection.commit();

        String bookingId = getBookingId();
        boolean exists = bookingDao.existByPk(connection, bookingId);
        assertTrue(exists, "Booking should exist with given PK");

        boolean notExists = bookingDao.existByPk(connection, "B999");
        assertFalse(notExists, "Booking should not exist with non-existent ID");
    }

    @Test
    void findByDate() throws SQLException, ClassNotFoundException {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        Booking booking1 = new Booking.BookingBuilder()
                .customerId(getCustomerId())
                .bookingDate(today)
                .pickupLocation("Location Q")
                .destination("Location R")
                .pickupTime(LocalTime.of(10, 0))
                .releaseTime(LocalTime.of(13, 0))
                .vehicleId(getVehicleId())
                .status(BookingStatus.pending)
                .distance(8.0)
                .fare(20.0)
                .discount(0.0)
                .tax(1.5)
                .netTotal(21.5)
                .userId(null)
                .createdAt(new Date())
                .build();

        Booking booking2 = new Booking.BookingBuilder()
                .customerId(getCustomerId())
                .bookingDate(tomorrow)
                .pickupLocation("Location S")
                .destination("Location T")
                .pickupTime(LocalTime.of(11, 0))
                .releaseTime(LocalTime.of(14, 0))
                .vehicleId(getVehicleId())
                .status(BookingStatus.pending)
                .distance(10.0)
                .fare(25.0)
                .discount(0.0)
                .tax(2.0)
                .netTotal(27.0)
                .userId(null)
                .createdAt(new Date())
                .build();

        bookingDao.add(connection, booking1);
        bookingDao.add(connection, booking2);
        connection.commit();

        List<Booking> todayBookings = bookingDao.findByDate(connection, today);
        assertEquals(1, todayBookings.size(), "Should find 1 booking for today");

        List<Booking> tomorrowBookings = bookingDao.findByDate(connection, tomorrow);
        assertEquals(1, tomorrowBookings.size(), "Should find 1 booking for tomorrow");
    }
}
