package com.project.megacitycab.dao.custom;

import com.project.megacitycab.dao.CrudDAO;
import com.project.megacitycab.entity.Vehicle;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface VehicleDAO extends CrudDAO<Vehicle> {
    boolean existByLicensePlate(Connection connection, Object... args) throws SQLException, ClassNotFoundException;
    List<Vehicle> getAvailableVehicles(Connection connection, LocalDate bookingDate, LocalTime pickupTime, LocalTime releaseTime) throws SQLException, ClassNotFoundException;
}
