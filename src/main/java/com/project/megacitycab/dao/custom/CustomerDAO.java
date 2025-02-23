package com.project.megacitycab.dao.custom;

import com.project.megacitycab.dao.CrudDAO;
import com.project.megacitycab.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    boolean existByEmail(Connection connection, Object... args) throws SQLException, ClassNotFoundException;
    boolean existByRegNo(Connection connection, Object... args) throws SQLException, ClassNotFoundException;
    boolean existByNic(Connection connection, Object... args) throws SQLException, ClassNotFoundException;

}
