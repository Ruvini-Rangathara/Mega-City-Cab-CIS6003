package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dao.DaoFactory;
import com.project.megacitycab.dao.DaoTypes;
import com.project.megacitycab.dao.custom.BookingDAO;
import com.project.megacitycab.dao.custom.CustomerDAO;
import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.entity.Booking;
import com.project.megacitycab.entity.Customer;
import com.project.megacitycab.service.custom.BookingService;
import com.project.megacitycab.util.DBUtil;
import com.project.megacitycab.util.converter.BookingConverter;
import com.project.megacitycab.util.exception.MegaCityCabException;
import com.project.megacitycab.util.exception.MegaCityCabExceptionType;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {
    private static final Logger logger = Logger.getLogger(BookingServiceImpl.class.getName());
    private final Connection connection = DBUtil.getConnection();
    private final BookingDAO bookingDAO;
    private final CustomerDAO customerDAO;

    public BookingServiceImpl() {
        this.bookingDAO = DaoFactory.getInstance().getDao(DaoTypes.BOOKING_DAO_IMPL);
        this.customerDAO = DaoFactory.getInstance().getDao(DaoTypes.CUSTOMER_DAO_IMPL);
    }

    @Override
    public boolean add(BookingDTO bookingDTO) throws MegaCityCabException {
        try {
            if (!validateBooking(bookingDTO)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_BOOKING_INPUTS);
            }
            boolean isAdded = bookingDAO.add(connection, BookingConverter.toEntity(bookingDTO));
            if (!isAdded) {
                throw new MegaCityCabException(MegaCityCabExceptionType.ERROR_ADDING_BOOKING);
            }

            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in add booking service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.ERROR_ADDING_BOOKING);
        } catch (MegaCityCabException e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in add booking service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean update(BookingDTO bookingDTO) throws MegaCityCabException, SQLException, ClassNotFoundException {
        if (!validateBooking(bookingDTO)) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_BOOKING_INPUTS);
        }

        if (!bookingDAO.existByPk(connection, bookingDTO.getId())) {
            throw new MegaCityCabException(MegaCityCabExceptionType.BOOKING_NOT_FOUND);
        }

        boolean isUpdated = bookingDAO.update(connection, BookingConverter.toEntity(bookingDTO));
        if (!isUpdated) {
            throw new MegaCityCabException(MegaCityCabExceptionType.ERROR_UPDATING_BOOKING);
        }

        return true;
    }

    @Override
    public boolean delete(Object... args) throws MegaCityCabException, SQLException, ClassNotFoundException {
        if (args.length == 0 || args[0] == null) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_BOOKING_INPUTS);
        }

        if (!bookingDAO.existByPk(connection, args[0])) {
            throw new MegaCityCabException(MegaCityCabExceptionType.BOOKING_NOT_FOUND);
        }

        return bookingDAO.delete(connection, args);

    }

    @Override
    public BookingDTO searchById(Object... args) throws MegaCityCabException, SQLException, ClassNotFoundException {
        if (args.length == 0 || args[0] == null) {
            throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_BOOKING_INPUTS);
        }
        Booking booking = bookingDAO.searchById(connection, args);

        // Return null if not found
        if (booking == null) {
            throw new MegaCityCabException(MegaCityCabExceptionType.BOOKING_NOT_FOUND);
        }
        return BookingConverter.toDTO(booking);
    }

    @Override
    public List<BookingDTO> getAll(Map<String, String> searchParams) throws MegaCityCabException {
        try {
            // Validate and clean search parameters
            Map<String, String> cleanParams = new HashMap<>();
            if (searchParams != null) {
                searchParams.forEach((key, value) -> {
                    if (value != null && !value.trim().isEmpty()) {
                        cleanParams.put(key, value.trim());
                    }
                });
            }

            List<Booking> bookings = bookingDAO.getAll(connection, cleanParams);
            return BookingConverter.toDTOList(bookings);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in get all bookings service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in get all bookings service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existByPk(Object... args) throws MegaCityCabException {
        try {
            if (args.length == 0 || args[0] == null) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_BOOKING_INPUTS);
            }

            return bookingDAO.existByPk(connection, args);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in exist by PK service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in exist by PK service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getLastInsertedId(Connection connection) throws SQLException, ClassNotFoundException {
        return bookingDAO.getLastInsertedId(connection);
    }

    @Override
    public List<BookingDTO> findByDate(LocalDate date) throws MegaCityCabException {
        try {
            // Validate date parameter
            if (date == null) {
                date = LocalDate.now();
            }

            List<Booking> bookings = bookingDAO.findByDate(connection, date);
            return BookingConverter.toDTOList(bookings);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in find bookings by date service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in find bookings by date service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to validate booking data
    private boolean validateBooking(BookingDTO booking) {
        // Basic validation to ensure required fields are present
        return booking != null && booking.getVehicleId() != null && !booking.getVehicleId().isEmpty() && booking.getBookingDate() != null && booking.getPickupTime() != null && booking.getReleaseTime() != null && booking.getPickupLocation() != null && !booking.getPickupLocation().isEmpty() && booking.getDestination() != null && !booking.getDestination().isEmpty() && booking.getDistance() > 0 && booking.getFare() > 0 && booking.getNetTotal() > 0 && booking.getStatus() != null;
    }

    @Override
    public double getTotalRevenue() throws MegaCityCabException, ClassNotFoundException {
        try {
            List<Booking> bookings = bookingDAO.getAll(connection, Map.of("status", "completed"));
            return bookings.stream()
                    .mapToDouble(Booking::getNetTotal)
                    .sum();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in getTotalRevenue", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public double getTotalVehicleEarnings() throws MegaCityCabException, ClassNotFoundException {
        try {
            List<Booking> bookings = bookingDAO.getAll(connection, Map.of("status", "completed"));
            return bookings.stream()
                    .mapToDouble(Booking::getFare)
                    .sum();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in getTotalVehicleEarnings", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public double getTotalDriverEarnings() throws MegaCityCabException, ClassNotFoundException {
        try {
            List<Booking> bookings = bookingDAO.getAll(connection, Map.of("status", "completed"));
            return bookings.stream()
                    .mapToDouble(b -> b.getFare() * 0.2) // Assuming 20% of fare goes to drivers
                    .sum();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in getTotalDriverEarnings", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public long getTotalCustomers() throws MegaCityCabException, ClassNotFoundException {
        try {
            List<Customer> customers = customerDAO.getAll(connection, new HashMap<>());
            return customers.size();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in getTotalCustomers", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public long getTotalBookings() throws MegaCityCabException, ClassNotFoundException {
        try {
            List<Booking> bookings = bookingDAO.getAll(connection, new HashMap<>());
            return bookings.size();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in getTotalBookings", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<BookingDTO> getRecentBookings(int limit) throws MegaCityCabException, ClassNotFoundException {
        try {
            List<Booking> bookings = bookingDAO.getAll(connection, new HashMap<>());
            return BookingConverter.toDTOList(
                    bookings.stream()
                            .sorted((b1, b2) -> {
                                int dateCompare = b2.getBookingDate().compareTo(b1.getBookingDate());
                                if (dateCompare != 0) return dateCompare;
                                return b2.getPickupTime().compareTo(b1.getPickupTime());
                            })
                            .limit(limit)
                            .collect(Collectors.toList())
            );
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in getRecentBookings", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public double getTotalExpenses() throws MegaCityCabException, ClassNotFoundException {
        try {
            List<Booking> bookings = bookingDAO.getAll(connection, Map.of("status", "completed"));
            return bookings.stream()
                    .mapToDouble(b -> b.getFare() * 0.1) // 10% of fare as expense (placeholder)
                    .sum();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in getTotalExpenses", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }


}
