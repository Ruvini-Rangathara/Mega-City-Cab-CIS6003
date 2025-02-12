package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.CustomerDAO;
import com.project.megacitycab.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDAO {
    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Customer searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean existByPk(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }
}
