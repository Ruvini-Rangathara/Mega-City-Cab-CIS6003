package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.BookingDAO;
import com.project.megacitycab.dao.util.CrudUtil;
import com.project.megacitycab.entity.Booking;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BookingDaoImpl implements BookingDAO {

    @Override
    public boolean add(Connection connection, Booking entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection,
                "INSERT INTO bookings (id, customerId, vehicleId, bookingDate, pickupTime, releaseTime, pickupLocation, destination, distance, fare, netTotal, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                entity.getId(),
                entity.getCustomerId(),
                entity.getVehicleId(),
                entity.getBookingDate(),
                entity.getPickupTime(),
                entity.getReleaseTime(),
                entity.getPickupLocation(),
                entity.getDestination(),
                entity.getDistance(),
                entity.getFare(),
                entity.getNetTotal(),
                entity.getStatus().toString()
        );
    }

    @Override
    public boolean update(Connection connection, Booking entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection,
                "UPDATE bookings SET customerId=?, vehicleId=?, bookingDate=?, pickupTime=?, releaseTime=?, pickupLocation=?, destination=?, distance=?, fare=?, netTotal=?, status=?, updatedAt=? WHERE id=?",
                entity.getCustomerId(),
                entity.getVehicleId(),
                entity.getBookingDate(),
                entity.getPickupTime(),
                entity.getReleaseTime(),
                entity.getPickupLocation(),
                entity.getDestination(),
                entity.getDistance(),
                entity.getFare(),
                entity.getNetTotal(),
                entity.getStatus().toString(),
                new Date(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Booking searchById(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection, "SELECT * FROM bookings WHERE id=? ", args[0]);

        if (result.next()) {
            return extractBookingFromResultSet(result);
        }

        return null;
    }

    @Override
    public List<Booking> getAll(Connection connection, Map<String, String> searchParams) throws SQLException, ClassNotFoundException {
        List<Booking> bookings = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT b.* FROM bookings b");

        // Add JOIN with customers table if searching by customer name or mobile
        if (searchParams != null && !searchParams.isEmpty() && searchParams.containsKey("searchCustomer")) {
            sql.append(" LEFT JOIN customers c ON b.customerId = c.id");
        }

        sql.append(" WHERE 1=1");

        // Add search conditions if provided
        if (searchParams != null && !searchParams.isEmpty()) {
            if (searchParams.containsKey("searchCustomer")) {
                sql.append(" AND (c.name LIKE ? OR c.mobileNo LIKE ?)");
                String searchTerm = "%" + searchParams.get("searchCustomer") + "%";
                params.add(searchTerm);
                params.add(searchTerm);
            }

            if (searchParams.containsKey("searchStatus")) {
                sql.append(" AND b.status = ?");
                params.add(searchParams.get("searchStatus"));
            }

            if (searchParams.containsKey("searchDate")) {
                sql.append(" AND DATE(b.bookingDate) = ?");
                params.add(searchParams.get("searchDate"));
            }
        }

        // Add ordering by created date in descending order
        sql.append(" ORDER BY b.createdAt DESC");

        // Execute the query using CrudUtil
        ResultSet result = CrudUtil.execute(connection, sql.toString(), params.toArray());

        while (result.next()) {
            bookings.add(extractBookingFromResultSet(result));
        }

        return bookings;
    }
    @Override
    public boolean existByPk(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection, "SELECT * FROM bookings WHERE id=?", args[0]);
        return result.next();
    }

    @Override
    public List<Booking> findByDate(Connection connection, LocalDate date) throws SQLException, ClassNotFoundException {
        List<Booking> bookings = new ArrayList<>();

        ResultSet result = CrudUtil.execute(connection,
                "SELECT * FROM bookings WHERE DATE(bookingDate) = ? ORDER BY pickupTime ASC",
                date);

        while (result.next()) {
            bookings.add(extractBookingFromResultSet(result));
        }
        return bookings;
    }

    // Helper method to extract Booking from ResultSet
    private Booking extractBookingFromResultSet(ResultSet result) throws SQLException {
        return new Booking.BookingBuilder()
                .id(result.getString("id"))
                .customerId(result.getString("customerId"))
                .vehicleId(result.getString("vehicleId"))
                .bookingDate(result.getObject("bookingDate", LocalDate.class))
                .pickupTime(result.getTime("pickupTime").toLocalTime())
                .releaseTime(result.getTime("releaseTime").toLocalTime())
                .pickupLocation(result.getString("pickupLocation"))
                .destination(result.getString("destination"))
                .distance(result.getDouble("distance"))
                .fare(result.getDouble("fare"))
                .netTotal(result.getDouble("netTotal"))
                .status(Enum.valueOf(com.project.megacitycab.constant.BookingStatus.class, result.getString("status")))
                .createdAt(result.getTimestamp("createdAt"))
                .updatedAt(result.getTimestamp("updatedAt"))
                .build();
    }
}
