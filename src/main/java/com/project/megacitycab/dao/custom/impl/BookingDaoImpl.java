package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.BookingDAO;
import com.project.megacitycab.entity.Booking;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookingDaoImpl implements BookingDAO {

    @Override
    public boolean add(Connection connection, Booking entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Connection connection, Booking entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Booking searchById(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAll(Connection connection, Map<String, String> searchParams) throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean existByPk(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }
}
