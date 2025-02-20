package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.service.custom.DriverService;

import java.sql.SQLException;
import java.util.List;

public class DriverServiceImpl implements DriverService {

    @Override
    public boolean add(DriverDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(DriverDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public DriverDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<DriverDTO> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean existByPk(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }
}
