package com.project.megacitycab.service.custom;

import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.service.CrudService;
import com.project.megacitycab.util.exception.MegaCityCabException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BookingService extends CrudService<BookingDTO> {
    List<BookingDTO> findByDate (LocalDate date) throws MegaCityCabException, SQLException;

    double getTotalRevenue() throws MegaCityCabException, ClassNotFoundException;

    double getTotalVehicleEarnings() throws MegaCityCabException, ClassNotFoundException;

    double getTotalDriverEarnings() throws MegaCityCabException, ClassNotFoundException;

    long getTotalCustomers() throws MegaCityCabException, ClassNotFoundException;

    long getTotalBookings() throws MegaCityCabException, ClassNotFoundException;

    List<BookingDTO> getRecentBookings(int i) throws MegaCityCabException, ClassNotFoundException;

    double getTotalExpenses() throws MegaCityCabException, ClassNotFoundException;
}
