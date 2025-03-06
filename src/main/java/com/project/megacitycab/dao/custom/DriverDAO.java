package com.project.megacitycab.dao.custom;

import com.project.megacitycab.dao.CrudDAO;
import com.project.megacitycab.entity.Driver;

import java.sql.Connection;
import java.sql.SQLException;

public interface DriverDAO extends CrudDAO<Driver> {
    boolean existByLicenseNo(Connection connection, Object... args) throws SQLException, ClassNotFoundException;
    String getLastInsertedId(Connection connection) throws SQLException, ClassNotFoundException;

}
