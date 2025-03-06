package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.constant.Role;
import com.project.megacitycab.dao.custom.UserDAO;
import com.project.megacitycab.dao.util.CrudUtil;
import com.project.megacitycab.entity.User;
import com.project.megacitycab.util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    @Override
    public boolean add(Connection connection, User entity) throws SQLException {
        try {
            return CrudUtil.execute(connection,
                    "INSERT INTO users (name, email, passwordHash, salt, role) VALUES (?,?,?,?,?)",
                    entity.getName(), entity.getEmail(), entity.getPassword(),
                    entity.getSalt(), entity.getRole().toString());
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error adding user", e);
            throw new SQLException(e);
        }
    }

    @Override
    public boolean update(Connection connection, User entity) throws SQLException {
        try {
            return CrudUtil.execute(connection,
                    "UPDATE users SET name=?, passwordHash=?, role=? WHERE id=?",
                    entity.getName(), entity.getPassword(), entity.getRole().toString(), entity.getId());
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error updating user", e);
            throw new SQLException(e);
        }
    }

    @Override
    public boolean delete(Connection connection, Object... args) throws SQLException {
        try {
            return CrudUtil.execute(connection,
                    "UPDATE users SET deletedAt=? WHERE id=?",
                    new Date(), args[0]);
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error deleting user", e);
            throw new SQLException(e);
        }
    }

    @Override
    public User searchById(Connection connection, Object... args) throws SQLException {
        try {
            ResultSet result = CrudUtil.execute(connection,
                    "SELECT * FROM users WHERE id=?", args[0]);
            if (result.next()) {
                return mapResultSetToUser(result);
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error searching user by ID", e);
            throw new SQLException(e);
        }
    }

    @Override
    public List<User> getAll(Connection connection, Map<String, String> searchParams) throws SQLException {
        try {
            ResultSet result = CrudUtil.execute(connection, "SELECT * FROM users");
            List<User> list = new ArrayList<>();
            while (result.next()) {
                list.add(mapResultSetToUser(result));
            }
            return list;
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error getting all users", e);
            throw new SQLException(e);
        }
    }

    @Override
    public boolean existByPk(Connection connection, Object... args) throws SQLException {
        try {
            ResultSet result = CrudUtil.execute(connection,
                    "SELECT * FROM users WHERE id=?", args[0]);
            return result.next();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error checking user existence by PK", e);
            throw new SQLException(e);
        }
    }

    @Override
    public boolean existByEmail(Connection connection, Object... args) throws SQLException {
        try {
            ResultSet result = CrudUtil.execute(connection,
                    "SELECT * FROM users WHERE email=?", args[0]);
            return result.next();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error checking user existence by email", e);
            throw new SQLException(e);
        }
    }

    @Override
    public User findByEmail(Connection connection, Object... args) throws SQLException {
        try {
            ResultSet result = CrudUtil.execute(connection,
                    "SELECT * FROM users WHERE email=?", args[0]);
            if (result.next()) {
                return mapResultSetToUser(result);
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error finding user by email", e);
            throw new SQLException(e);
        }
    }

    private User mapResultSetToUser(ResultSet result) throws SQLException {
        return new User.UserBuilder()
                .id(result.getString("id"))
                .name(result.getString("name"))
                .email(result.getString("email"))
                .password(result.getString("passwordHash"))
                .salt(result.getString("salt"))
                .role(Role.valueOf(result.getString("role")))
                .createdAt(result.getDate("createdAt"))
                .updatedAt(result.getDate("updatedAt"))
                .deletedAt(result.getDate("deletedAt"))
                .build();
    }
}
