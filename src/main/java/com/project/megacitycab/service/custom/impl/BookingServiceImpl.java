package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dao.DaoFactory;
import com.project.megacitycab.dao.DaoTypes;
import com.project.megacitycab.dao.custom.BookingDAO;
import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.entity.Booking;
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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingServiceImpl implements BookingService {
    private static final Logger logger = Logger.getLogger(BookingServiceImpl.class.getName());
    private final Connection connection = DBUtil.getConnection();
    private final BookingDAO bookingDAO;

    public BookingServiceImpl() {
        this.bookingDAO = DaoFactory.getInstance().getDao(DaoTypes.BOOKING_DAO_IMPL);
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
    public boolean update(BookingDTO bookingDTO) throws MegaCityCabException {
        try {
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in update booking service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.ERROR_UPDATING_BOOKING);
        } catch (MegaCityCabException e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in update booking service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean delete(Object... args) throws MegaCityCabException {
        try {
            if (args.length == 0 || args[0] == null) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_BOOKING_INPUTS);
            }

            if (!bookingDAO.existByPk(connection, args[0])) {
                throw new MegaCityCabException(MegaCityCabExceptionType.BOOKING_NOT_FOUND);
            }

            return bookingDAO.delete(connection, args);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in delete booking service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.ERROR_DELETING_BOOKING);
        } catch (MegaCityCabException e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in delete booking service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BookingDTO searchById(Object... args) throws MegaCityCabException {
        try {
            if (args.length == 0 || args[0] == null) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_BOOKING_INPUTS);
            }

            Booking booking = bookingDAO.searchById(connection, args);

            // Return null if not found
            if (booking == null) {
                throw new MegaCityCabException(MegaCityCabExceptionType.BOOKING_NOT_FOUND);
            }

            return BookingConverter.toDTO(booking);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error in search booking by ID service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in search booking by ID service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
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
        return booking != null
                && booking.getCustomerId() != null && !booking.getCustomerId().isEmpty()
                && booking.getVehicleId() != null && !booking.getVehicleId().isEmpty()
                && booking.getBookingDate() != null
                && booking.getPickupTime() != null
                && booking.getReleaseTime() != null
                && booking.getPickupLocation() != null && !booking.getPickupLocation().isEmpty()
                && booking.getDestination() != null && !booking.getDestination().isEmpty()
                && booking.getDistance() > 0
                && booking.getFare() > 0
                && booking.getNetTotal() > 0
                && booking.getStatus() != null;
    }
}
