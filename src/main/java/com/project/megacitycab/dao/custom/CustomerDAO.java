package com.project.megacitycab.dao.custom;

import com.project.megacitycab.dao.CrudDAO;
import com.project.megacitycab.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    boolean existByEmail(Object... args) throws SQLException, ClassNotFoundException;
    boolean existByRegNo(Object... args) throws SQLException, ClassNotFoundException;
    boolean existByNic(Object... args) throws SQLException, ClassNotFoundException;

}
