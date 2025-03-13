package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.constant.VehicleStatus;
import com.project.megacitycab.dao.custom.VehicleDAO;
import com.project.megacitycab.dao.util.CrudUtil;
import com.project.megacitycab.entity.Vehicle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleDaoImpl implements VehicleDAO {
    private static final Logger logger = Logger.getLogger(VehicleDaoImpl.class.getName());

    public VehicleDaoImpl() {
    }

    @Override
    public boolean add(Connection connection, Vehicle entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(

                connection, "INSERT INTO vehicles (licensePlate, driverId, model, brand, capacity, color,pricePerKm,  status) VALUES (?,?,?,?,?,?,?,?)", entity.getLicensePlate(), entity.getDriverId(), entity.getModel(), entity.getBrand(), entity.getCapacity(), entity.getColor(), entity.getPricePerKm(), entity.getStatus().toString());
    }

    @Override
    public boolean update(Connection connection, Vehicle entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "UPDATE vehicles SET licensePlate=?, driverId=?, model=?, brand=?, capacity=?, color=?, pricePerKm=?, status=? WHERE id=?", entity.getLicensePlate(), entity.getDriverId(), entity.getModel(), entity.getBrand(), entity.getCapacity(), entity.getColor(), entity.getPricePerKm(), entity.getStatus().toString(), entity.getId());
    }

    @Override
    public boolean delete(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "UPDATE vehicles SET deletedAt=? WHERE id=?", new Date(), args[0]);
    }

    @Override
    public Vehicle searchById(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection, "SELECT * FROM vehicles WHERE id=? AND deletedAt IS NULL", args[0]);

        if (result.next()) {
            return new Vehicle.VehicleBuilder().id(result.getString("id")).licensePlate(result.getString("licensePlate")).driverId(result.getString("driverId")).model(result.getString("model")).brand(result.getString("brand")).capacity(result.getInt("capacity")).color(result.getString("color")).pricePerKm(result.getDouble("pricePerKm")).status(VehicleStatus.valueOf(result.getString("status"))).createdAt(result.getString("createdAt")).updatedAt(result.getString("updatedAt")).deletedAt(result.getString("deletedAt")).build();
        }
        return null;
    }

    @Override
    public List<Vehicle> getAll(Connection connection, Map<String, String> searchParams) throws SQLException, ClassNotFoundException {
        List<Vehicle> vehicles = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM vehicles WHERE deletedAt IS NULL");

        if (searchParams != null && !searchParams.isEmpty()) {
            if (searchParams.containsKey("licensePlate") && searchParams.get("licensePlate") != null && !searchParams.get("licensePlate").trim().isEmpty()) {
                sql.append(" AND licensePlate LIKE ?");
                params.add("%" + searchParams.get("licensePlate").trim() + "%");
            }
            if (searchParams.containsKey("model") && searchParams.get("model") != null && !searchParams.get("model").trim().isEmpty()) {
                sql.append(" AND model LIKE ?");
                params.add("%" + searchParams.get("model").trim() + "%");
            }
            if (searchParams.containsKey("brand") && searchParams.get("brand") != null && !searchParams.get("brand").trim().isEmpty()) {
                sql.append(" AND brand LIKE ?");
                params.add("%" + searchParams.get("brand").trim() + "%");
            }
            if (searchParams.containsKey("status") && searchParams.get("status") != null && !searchParams.get("status").trim().isEmpty()) {
                sql.append(" AND status = ?");
                params.add(searchParams.get("status").trim());
            }
            if (searchParams.containsKey("driverId") && searchParams.get("driverId") != null && !searchParams.get("driverId").trim().isEmpty()) {
                sql.append(" AND driverId = ?");
                params.add(searchParams.get("driverId").trim());
            }
        }

        sql.append(" ORDER BY createdAt DESC");

        ResultSet result = CrudUtil.execute(connection, sql.toString(), params.toArray());

        while (result.next()) {
            Vehicle vehicle = new Vehicle.VehicleBuilder()
                    .id(result.getString("id"))
                    .licensePlate(result.getString("licensePlate"))
                    .driverId(result.getString("driverId"))
                    .model(result.getString("model"))
                    .brand(result.getString("brand"))
                    .capacity(result.getInt("capacity"))
                    .color(result.getString("color"))
                    .pricePerKm(result.getDouble("pricePerKm"))
                    .status(VehicleStatus.valueOf(result.getString("status")))
                    .createdAt(result.getString("createdAt"))
                    .updatedAt(result.getString("updatedAt"))
                    .deletedAt(result.getString("deletedAt"))
                    .build();
            vehicles.add(vehicle);
        }

        return vehicles;
    }

    @Override
    public boolean existByPk(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection, "SELECT * FROM vehicles WHERE id=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }

    @Override
    public String getLastInsertedId(Connection connection) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM vehicles ORDER BY createdAt DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(connection, sql);
        if (rs.next()) {
            return rs.getString(1);
        }
        throw new SQLException("Failed to get last inserted ID");
    }

    @Override
    public boolean existByLicensePlate(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection, "SELECT * FROM vehicles WHERE licensePlate=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }

    @Override
    public List<Vehicle> getAvailableVehicles(Connection connection, LocalDate bookingDate, LocalTime pickupTime, LocalTime releaseTime) throws SQLException {
        List<Vehicle> availableVehicles = new ArrayList<>();
        String sql = "{CALL mega_city_cab.GetAvailableVehicles(?, ?, ?, ?)}";

        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(bookingDate.atStartOfDay()));
            stmt.setTime(2, java.sql.Time.valueOf(pickupTime));
            stmt.setTime(3, java.sql.Time.valueOf(releaseTime));
            stmt.registerOutParameter(4, java.sql.Types.BOOLEAN);

            boolean hasResults = stmt.execute();
            if (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        Vehicle vehicle = new Vehicle.VehicleBuilder().id(rs.getString("id")).licensePlate(rs.getString("licensePlate")).driverId(rs.getString("driverId")).model(rs.getString("model")).brand(rs.getString("brand")).capacity(rs.getInt("capacity")).color(rs.getString("color")).pricePerKm(rs.getDouble("pricePerKm")).build();
                        availableVehicles.add(vehicle);
                    }
                }
            }

            boolean result = stmt.getBoolean(4);
            if (!result) {
                logger.log(Level.WARNING, "GetAvailableVehicles returned false; no vehicles may be available.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching available vehicles: " + e.getMessage());
            throw e;
        }

        return availableVehicles;
    }

}
