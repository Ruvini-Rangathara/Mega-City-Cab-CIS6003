package com.project.megacitycab.dao.custom;

import com.project.megacitycab.dao.CrudDAO;
import com.project.megacitycab.entity.Vehicle;

import java.sql.Connection;
import java.sql.SQLException;

public interface VehicleDAO extends CrudDAO<Vehicle> {
    boolean existByLicensePlate(Connection connection, Object... args) throws SQLException, ClassNotFoundException;
}
