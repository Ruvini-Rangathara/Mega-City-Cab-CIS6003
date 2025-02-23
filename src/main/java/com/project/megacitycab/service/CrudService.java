package com.project.megacitycab.service;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.exception.MegaCityCabException;

import java.util.List;
import java.util.Map;

public interface CrudService<T extends SuperDTO> extends SuperService {
    boolean add(T entity) throws MegaCityCabException;

    boolean update(T entity) throws MegaCityCabException;

    boolean delete(Object... args) throws MegaCityCabException;

    T searchById(Object... args) throws MegaCityCabException;

    List<T> getAll(Map<String, String> searchParams) throws MegaCityCabException;

    boolean existByPk(Object... args) throws MegaCityCabException;
}
