package com.project.megacitycab.dao.custom;

import com.project.megacitycab.dao.CrudDAO;
import com.project.megacitycab.entity.Booking;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BookingDAO extends CrudDAO<Booking> {
    List<Booking> findByDate(Connection connection, LocalDate date) throws SQLException, ClassNotFoundException;
}
