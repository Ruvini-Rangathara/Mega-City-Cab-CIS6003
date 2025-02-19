package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.constant.Role;
import com.project.megacitycab.dao.custom.UserDAO;
import com.project.megacitycab.dao.util.CrudUtil;
import com.project.megacitycab.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements UserDAO {
    @Override
    public boolean add(User entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO users (name, email, passwordHash, salt, role) VALUES (?,?,?,?,?)", entity.getName(), entity.getEmail(), entity.getPassword(),entity.getSalt(), entity.getRole().toString());
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE users SET name=? passwordHash=?, role=? WHERE id=?", entity.getName(), entity.getPassword(), entity.getRole().toString(), entity.getId());
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE users SET deletedAt=? WHERE id=?", new Date(), args[0]);
    }

    @Override
    public User searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM users WHERE id=?", args[0]);
        if (result.next()) {
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
        return null;
    }


    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM users");
        List<User> list = new ArrayList<>();

        while (result.next()) {
            User user = new User.UserBuilder()
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

            list.add(user);
        }

        return list;
    }



    @Override
    public boolean existByPk(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM users WHERE id=?", args[0]);
        return result.next();
    }

    @Override
    public boolean existByEmail(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM users WHERE email=?", args[0]);
        return result.next();
    }

    @Override
    public User findByEmail(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM users WHERE email=?", args[0]);
        if (result.next()) {
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
        return null;
    }
}
