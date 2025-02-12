package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.BookingDAO;
import com.project.megacitycab.entity.Booking;

import java.sql.SQLException;
import java.util.List;

public class BookingDaoImpl implements BookingDAO {

    @Override
    public boolean add(Booking entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Booking entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Booking searchById(Object... args) throws SQLException, ClassNotFoundException {
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
