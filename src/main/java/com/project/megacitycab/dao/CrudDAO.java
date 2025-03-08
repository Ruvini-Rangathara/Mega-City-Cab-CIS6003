package com.project.megacitycab.dao;

import com.project.megacitycab.entity.SuperEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CrudDAO<T extends SuperEntity> extends SuperDAO {

    boolean add(Connection connection, T entity) throws SQLException, ClassNotFoundException;

    boolean update( Connection connection,T entity) throws SQLException, ClassNotFoundException;

    boolean delete(Connection connection, Object... args) throws SQLException, ClassNotFoundException;

    T searchById(Connection connection, Object... args) throws SQLException, ClassNotFoundException;

    List getAll(Connection connection, Map<String, String> searchParams) throws SQLException, ClassNotFoundException;

    boolean existByPk(Connection connection, Object... args) throws SQLException, ClassNotFoundException;

    String getLastInsertedId(Connection connection) throws SQLException, ClassNotFoundException;

}
