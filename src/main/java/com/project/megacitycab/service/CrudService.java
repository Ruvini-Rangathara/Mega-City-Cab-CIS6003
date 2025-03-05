package com.project.megacitycab.service;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.exception.MegaCityCabException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CrudService<T extends SuperDTO> extends SuperService {
    boolean add(T entity) throws MegaCityCabException, SQLException;

    boolean update(T entity) throws MegaCityCabException, SQLException;

    boolean delete(Object... args) throws MegaCityCabException, SQLException;

    T searchById(Object... args) throws MegaCityCabException, SQLException;

    List<T> getAll(Map<String, String> searchParams) throws MegaCityCabException, SQLException;

    boolean existByPk(Object... args) throws MegaCityCabException, SQLException;
}
