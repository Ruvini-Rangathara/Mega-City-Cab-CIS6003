package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.UserDAO;
import com.project.megacitycab.entity.User;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDAO {
    @Override
    public boolean add(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public User searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean existByPk(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }
}
