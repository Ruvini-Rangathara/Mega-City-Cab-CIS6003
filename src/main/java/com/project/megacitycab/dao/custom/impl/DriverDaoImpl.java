package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.DriverDAO;
import com.project.megacitycab.entity.Driver;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DriverDaoImpl implements DriverDAO {
    @Override
    public boolean add(Driver entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Driver entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Driver searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAll(Map<String, String> searchParams) throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean existByPk(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }
}
