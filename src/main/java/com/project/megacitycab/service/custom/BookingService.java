package com.project.megacitycab.service.custom;

import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.service.CrudService;
import com.project.megacitycab.util.exception.MegaCityCabException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BookingService extends CrudService<BookingDTO> {
    List<BookingDTO> findByDate (LocalDate date) throws MegaCityCabException, SQLException;
}
