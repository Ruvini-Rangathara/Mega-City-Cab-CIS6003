package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.service.custom.DriverService;

import java.sql.SQLException;
import java.util.List;

public class DriverServiceImpl implements DriverService {
    @Override
    public boolean add(SuperDTO entity) {
        return false;
    }

    @Override
    public boolean update(SuperDTO entity)  {
        return false;
    }

    @Override
    public boolean delete(Object... args)  {
        return false;
    }

    @Override
    public SuperDTO searchById(Object... args)  {
        return null;
    }

    @Override
    public List getAll()  {
        return List.of();
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }
}
