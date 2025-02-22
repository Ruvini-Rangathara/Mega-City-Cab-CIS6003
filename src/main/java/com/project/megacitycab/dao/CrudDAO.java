package com.project.megacitycab.dao;

import com.project.megacitycab.entity.SuperEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CrudDAO<T extends SuperEntity> extends SuperDAO {

    boolean add(T entity) throws SQLException, ClassNotFoundException;
    boolean update(T entity) throws SQLException, ClassNotFoundException;
    boolean delete(Object... args) throws SQLException, ClassNotFoundException;
    T searchById(Object... args) throws SQLException, ClassNotFoundException;
    List getAll(Map<String, String> searchParams) throws SQLException, ClassNotFoundException;
    boolean existByPk(Object... args) throws SQLException, ClassNotFoundException;

}
