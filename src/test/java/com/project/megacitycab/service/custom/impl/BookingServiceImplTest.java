package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.constant.BookingStatus;
import com.project.megacitycab.constant.VehicleStatus;
import com.project.megacitycab.dao.DaoFactory;
import com.project.megacitycab.dao.DaoTypes;
import com.project.megacitycab.dao.custom.CustomerDAO;
import com.project.megacitycab.dao.custom.DriverDAO;
import com.project.megacitycab.dao.custom.VehicleDAO;
import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.dto.CustomerDTO;
import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.util.DBUtil;
import com.project.megacitycab.util.converter.CustomerConverter;
import com.project.megacitycab.util.converter.DriverConverter;
import com.project.megacitycab.util.converter.VehicleConverter;
import com.project.megacitycab.util.exception.MegaCityCabException;
import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceImplTest {

    private BookingServiceImpl bookingService;
    private CustomerDAO customerDAO;
    private DriverDAO driverDAO;
    private VehicleDAO vehicleDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        bookingService = new BookingServiceImpl();
        customerDAO = DaoFactory.getInstance().getDao(DaoTypes.CUSTOMER_DAO_IMPL);
        driverDAO = DaoFactory.getInstance().getDao(DaoTypes.DRIVER_DAO_IMPL);
        vehicleDAO = DaoFactory.getInstance().getDao(DaoTypes.VEHICLE_DAO_IMPL);
        connection = DBUtil.getConnection();
        connection.setAutoCommit(false);

        // Clean up tables
        connection.prepareStatement("DELETE FROM bookings").executeUpdate();
        connection.prepareStatement("DELETE FROM customers").executeUpdate();
        connection.prepareStatement("DELETE FROM vehicles").executeUpdate();
        connection.prepareStatement("DELETE FROM drivers").executeUpdate();
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.rollback();
            connection.close();
        }
    }

    private String[] setupBookingPrerequisites() throws SQLException, ClassNotFoundException {
        // Add a customer
        CustomerDTO customer = new CustomerDTO.CustomerDTOBuilder()
                .name("Test Customer")
                .registrationNo("C001")
                .email("test@customer.com")
                .nic("123456789V")
                .dob(new Date())
                .address("123, Test Street, Test City")
                .mobileNo("0771234567")
                .build();
        customerDAO.add(connection, CustomerConverter.toEntity(customer));
        String customerId = customerDAO.getLastInsertedId(connection);

        // Add a driver
        DriverDTO driver = new DriverDTO.DriverDTOBuilder()
                .licenseNo("DL001")
                .name("Test Driver")
                .mobileNo("0771234567")
                .email("driver@example.com")
                .experience(5)
                .build();
        driverDAO.add(connection, DriverConverter.toEntity(driver));
        String driverId = driverDAO.getLastInsertedId(connection);

        // Add a vehicle
        VehicleDTO vehicle = new VehicleDTO.VehicleDTOBuilder()
                .licensePlate("ABC123")
                .driverId(driverId)
                .model("Camry")
                .brand("Toyota")
                .color("Black")
                .capacity(4)
                .status(VehicleStatus.available)
                .pricePerKm(100.0)
                .build();
        vehicleDAO.add(connection, VehicleConverter.toEntity(vehicle));
        String vehicleId = vehicleDAO.getLastInsertedId(connection);

        connection.commit();
        return new String[]{customerId, vehicleId};
    }

    private String addBookingAndGetId(BookingDTO booking) throws MegaCityCabException, SQLException, ClassNotFoundException {
        bookingService.add(booking);
        String bookingId = bookingService.getLastInsertedId(connection);
        BookingDTO addedBooking = bookingService.searchById(bookingId);
        if (addedBooking == null || addedBooking.getId() == null) {
            throw new RuntimeException("Failed to retrieve added booking ID");
        }
        return addedBooking.getId();
    }

    @Test
    void add() throws MegaCityCabException, SQLException, ClassNotFoundException {
        String[] ids = setupBookingPrerequisites();
        String customerId = ids[0];
        String vehicleId = ids[1];

        BookingDTO booking = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .bookingDate(LocalDate.now())
                .pickupLocation("Location A")
                .destination("Location B")
                .pickupTime(LocalTime.of(10, 0))
                .releaseTime(LocalTime.of(12, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.pending)
                .distance(10.5)
                .fare(1000.0)
                .discount(50.0)
                .tax(100.0)
                .netTotal(1050.0)
                .build();

        boolean result = bookingService.add(booking);
        assertTrue(result);

        String bookingId = bookingService.getLastInsertedId(connection);
        BookingDTO addedBooking = bookingService.searchById(bookingId);
        assertNotNull(addedBooking);
        assertEquals(customerId, addedBooking.getCustomerId());
        assertEquals("Location A", addedBooking.getPickupLocation());
    }

    @Test
    void update() throws MegaCityCabException, SQLException, ClassNotFoundException {
        String[] ids = setupBookingPrerequisites();
        String customerId = ids[0];
        String vehicleId = ids[1];

        BookingDTO booking = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .bookingDate(LocalDate.now())
                .pickupLocation("Location X")
                .destination("Location Y")
                .pickupTime(LocalTime.of(9, 0))
                .releaseTime(LocalTime.of(11, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.pending)
                .distance(15.0)
                .fare(1500.0)
                .discount(0.0)
                .tax(150.0)
                .netTotal(1650.0)
                .build();
        String bookingId = addBookingAndGetId(booking);

        BookingDTO updatedBooking = new BookingDTO.BookingDTOBuilder()
                .id(bookingId)
                .customerId(customerId)
                .bookingDate(LocalDate.now())
                .pickupLocation("Updated Location X")
                .destination("Updated Location Y")
                .pickupTime(LocalTime.of(10, 0))
                .releaseTime(LocalTime.of(12, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.confirmed)
                .distance(20.0)
                .fare(2000.0)
                .discount(100.0)
                .tax(200.0)
                .netTotal(2100.0)
                .build();

        boolean result = bookingService.update(updatedBooking);
        assertTrue(result);

        BookingDTO fetchedBooking = bookingService.searchById(bookingId);
        assertNotNull(fetchedBooking);
        assertEquals("Updated Location X", fetchedBooking.getPickupLocation());
        assertEquals(BookingStatus.confirmed, fetchedBooking.getStatus());
    }

    @Test
    void searchById() throws MegaCityCabException, SQLException, ClassNotFoundException {
        String[] ids = setupBookingPrerequisites();
        String customerId = ids[0];
        String vehicleId = ids[1];

        BookingDTO booking = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .bookingDate(LocalDate.now())
                .pickupLocation("Location E")
                .destination("Location F")
                .pickupTime(LocalTime.of(14, 0))
                .releaseTime(LocalTime.of(16, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.pending)
                .distance(12.0)
                .fare(1200.0)
                .discount(0.0)
                .tax(120.0)
                .netTotal(1320.0)
                .build();
        String bookingId = addBookingAndGetId(booking);

        BookingDTO foundBooking = bookingService.searchById(bookingId);
        assertEquals(bookingId, foundBooking.getId());

        try{
            bookingService.searchById("B999");
        }catch (Exception e){
            assertTrue(true);
        }

    }

    @Test
    void getAll() throws MegaCityCabException, SQLException, ClassNotFoundException {
        String[] ids = setupBookingPrerequisites();
        String customerId = ids[0];
        String vehicleId = ids[1];

        BookingDTO booking1 = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .bookingDate(LocalDate.now())
                .pickupLocation("Location G")
                .destination("Location H")
                .pickupTime(LocalTime.of(7, 0))
                .releaseTime(LocalTime.of(9, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.pending)
                .distance(5.0)
                .fare(500.0)
                .discount(0.0)
                .tax(50.0)
                .netTotal(550.0)
                .build();
        String bookingId1 = addBookingAndGetId(booking1);

        BookingDTO booking2 = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .bookingDate(LocalDate.now())
                .pickupLocation("Location I")
                .destination("Location J")
                .pickupTime(LocalTime.of(11, 0))
                .releaseTime(LocalTime.of(13, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.confirmed)
                .distance(7.0)
                .fare(700.0)
                .discount(0.0)
                .tax(70.0)
                .netTotal(770.0)
                .build();
        String bookingId2 = addBookingAndGetId(booking2);

        List<BookingDTO> allBookings = bookingService.getAll(null);
        assertEquals(2, allBookings.size());

        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("searchStatus", "confirmed");
        List<BookingDTO> filteredBookings = bookingService.getAll(searchParams);
        assertEquals(1, filteredBookings.size());
    }

    @Test
    void existByPk() throws MegaCityCabException, SQLException, ClassNotFoundException {
        String[] ids = setupBookingPrerequisites();
        String customerId = ids[0];
        String vehicleId = ids[1];

        BookingDTO booking = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .bookingDate(LocalDate.now())
                .pickupLocation("Location K")
                .destination("Location L")
                .pickupTime(LocalTime.of(15, 0))
                .releaseTime(LocalTime.of(17, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.pending)
                .distance(9.0)
                .fare(900.0)
                .discount(0.0)
                .tax(90.0)
                .netTotal(990.0)
                .build();
        String bookingId = addBookingAndGetId(booking);

        boolean exists = bookingService.existByPk(bookingId);
        assertTrue(exists);

        boolean notExists = bookingService.existByPk("B999");
        assertFalse(notExists);
    }

    @Test
    void getLastInsertedId() throws MegaCityCabException, SQLException, ClassNotFoundException {
        String[] ids = setupBookingPrerequisites();
        String customerId = ids[0];
        String vehicleId = ids[1];

        BookingDTO booking = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .bookingDate(LocalDate.now())
                .pickupLocation("Location M")
                .destination("Location N")
                .pickupTime(LocalTime.of(16, 0))
                .releaseTime(LocalTime.of(18, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.pending)
                .distance(6.0)
                .fare(600.0)
                .discount(0.0)
                .tax(60.0)
                .netTotal(660.0)
                .build();
        bookingService.add(booking);

        String lastId = bookingService.getLastInsertedId(connection);
        assertNotNull(lastId);

        BookingDTO fetchedBooking = bookingService.searchById(lastId);
        assertNotNull(fetchedBooking);
        assertEquals(lastId, fetchedBooking.getId());
        assertEquals("Location M", fetchedBooking.getPickupLocation());
    }

    @Test
    void findByDate() throws MegaCityCabException, SQLException, ClassNotFoundException {
        String[] ids = setupBookingPrerequisites();
        String customerId = ids[0];
        String vehicleId = ids[1];

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        BookingDTO booking1 = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .bookingDate(today)
                .pickupLocation("Location O")
                .destination("Location P")
                .pickupTime(LocalTime.of(8, 0))
                .releaseTime(LocalTime.of(10, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.pending)
                .distance(11.0)
                .fare(1100.0)
                .discount(0.0)
                .tax(110.0)
                .netTotal(1210.0)
                .build();
        String bookingId1 = addBookingAndGetId(booking1);

        BookingDTO booking2 = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .bookingDate(tomorrow)
                .pickupLocation("Location Q")
                .destination("Location R")
                .pickupTime(LocalTime.of(9, 0))
                .releaseTime(LocalTime.of(11, 0))
                .vehicleId(vehicleId)
                .status(BookingStatus.confirmed)
                .distance(13.0)
                .fare(1300.0)
                .discount(0.0)
                .tax(130.0)
                .netTotal(1430.0)
                .build();
        String bookingId2 = addBookingAndGetId(booking2);

        List<BookingDTO> bookingsToday = bookingService.findByDate(today);
        assertEquals(bookingId1, bookingsToday.get(0).getId());
    }
}
