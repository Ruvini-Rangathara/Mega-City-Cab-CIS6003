package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.service.custom.BookingService;

import java.sql.SQLException;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    @Override
    public boolean add(BookingDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(BookingDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BookingDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
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
