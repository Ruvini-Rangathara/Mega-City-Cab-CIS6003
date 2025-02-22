package com.project.megacitycab.service;

import com.project.megacitycab.dto.SuperDTO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CrudService<T extends SuperDTO> extends SuperService {
    boolean add(T entity) throws SQLException, ClassNotFoundException;

    boolean update(T entity) throws SQLException, ClassNotFoundException;

    boolean delete(Object... args) throws SQLException, ClassNotFoundException;

    T searchById(Object... args) throws SQLException, ClassNotFoundException;

    List<T> getAll(Map<String, String> searchParams) throws SQLException, ClassNotFoundException;

    boolean existByPk(Object... args) throws SQLException, ClassNotFoundException;
}
