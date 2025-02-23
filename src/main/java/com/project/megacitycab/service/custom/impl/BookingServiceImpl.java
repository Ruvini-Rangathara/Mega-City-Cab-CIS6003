package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.service.custom.BookingService;
import com.project.megacitycab.util.exception.MegaCityCabException;

import java.util.List;
import java.util.Map;

public class BookingServiceImpl implements BookingService {

    @Override
    public boolean add(BookingDTO entity) throws MegaCityCabException {
        return false;
    }

    @Override
    public boolean update(BookingDTO entity) throws MegaCityCabException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws MegaCityCabException {
        return false;
    }

    @Override
    public BookingDTO searchById(Object... args) throws MegaCityCabException {
        return null;
    }

    @Override
    public List<BookingDTO> getAll(Map<String, String> searchParams) throws MegaCityCabException {
        return List.of();
    }

    @Override
    public boolean existByPk(Object... args) throws MegaCityCabException {
        return false;
    }
}
