package com.project.megacitycab.dao.custom;

import com.project.megacitycab.dao.CrudDAO;
import com.project.megacitycab.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    boolean existByEmail(Object... args) throws SQLException, ClassNotFoundException;

}
