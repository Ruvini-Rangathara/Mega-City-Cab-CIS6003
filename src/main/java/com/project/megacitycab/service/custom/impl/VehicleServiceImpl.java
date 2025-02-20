package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.service.custom.VehicleService;

import java.sql.SQLException;
import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    @Override
    public boolean add(VehicleDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(VehicleDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public VehicleDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<VehicleDTO> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean existByPk(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }
}
