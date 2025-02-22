package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.VehicleDAO;
import com.project.megacitycab.entity.Vehicle;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class VehicleDaoImpl implements VehicleDAO {
    @Override
    public boolean add(Vehicle entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Vehicle entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Vehicle searchById(Object... args) throws SQLException, ClassNotFoundException {
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
