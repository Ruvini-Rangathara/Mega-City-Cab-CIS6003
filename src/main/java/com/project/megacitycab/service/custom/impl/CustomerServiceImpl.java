package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.service.custom.CustomerService;

import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean add(SuperDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(SuperDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public SuperDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }
}
