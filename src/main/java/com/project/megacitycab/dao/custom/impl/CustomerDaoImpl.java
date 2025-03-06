package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.CustomerDAO;
import com.project.megacitycab.dao.util.CrudUtil;
import com.project.megacitycab.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CustomerDaoImpl implements CustomerDAO {
    @Override
    public boolean add( Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection,"INSERT INTO customers (registrationNo, name, address, nic, dob, mobileNo, email) VALUES (?,?,?,?,?,?,?)", entity.getRegistrationNo(), entity.getName(), entity.getAddress(), entity.getNic(), entity.getDob(), entity.getMobileNo(), entity.getEmail());
    }

    @Override
    public boolean update( Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection,"UPDATE customers SET name=?, address=?, nic=?, dob=?, mobileNo=?, email=? WHERE id=?", entity.getName(), entity.getAddress(), entity.getNic(), entity.getDob(), entity.getMobileNo(), entity.getEmail(), entity.getId());
    }

    @Override
    public boolean delete(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection,"UPDATE customers SET deletedAt=? WHERE id=?", new Date(), args[0]);
    }

    @Override
    public Customer searchById(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection,"SELECT * FROM customers WHERE id=?  AND deletedAt IS NULL", args[0]);

        if (result.next()) {
            return new Customer.CustomerBuilder()
                    .id(result.getString("id"))
                    .registrationNo(result.getString("registrationNo"))
                    .name(result.getString("name"))
                    .address(result.getString("address"))
                    .nic(result.getString("nic"))
                    .dob(result.getDate("dob"))
                    .mobileNo(result.getString("mobileNo"))
                    .email(result.getString("email"))
                    .createdAt(result.getString("createdAt"))
                    .updatedAt(result.getString("updatedAt"))
                    .deletedAt(result.getString("deletedAt"))
                    .build();
        }

        return null;
    }

    @Override
    public List<Customer> getAll( Connection connection, Map<String, String> searchParams) throws SQLException, ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM customers WHERE deletedAt IS NULL");

        // Add search conditions if provided
        if (searchParams != null) {
            if (searchParams.containsKey("registrationNo")) {
                sql.append(" AND registration_no LIKE ?");
                params.add("%" + searchParams.get("registrationNo") + "%");
            }
            if (searchParams.containsKey("name")) {
                sql.append(" AND name LIKE ?");
                params.add("%" + searchParams.get("name") + "%");
            }
            if (searchParams.containsKey("email")) {
                sql.append(" AND email LIKE ?");
                params.add("%" + searchParams.get("email") + "%");
            }
            if (searchParams.containsKey("mobileNo")) {
                sql.append(" AND mobile_no LIKE ?");
                params.add("%" + searchParams.get("mobileNo") + "%");
            }
        }

        // Add ordering by created date in descending order
        sql.append(" ORDER BY createdAt DESC");

        // Execute the query using CrudUtil
        ResultSet result = CrudUtil.execute(connection, sql.toString(), params.toArray());

        while (result.next()) {
            Customer customer = new Customer.CustomerBuilder().
                    id(result.getString("id")).
                    registrationNo(result.getString("registrationNo")).
                    name(result.getString("name")).
                    address(result.getString("address")).
                    nic(result.getString("nic")).
                    dob(result.getDate("dob")).
                    mobileNo(result.getString("mobileNo")).
                    email(result.getString("email")).
                    createdAt(result.getString("createdAt")).
                    updatedAt(result.getString("updatedAt")).
                    deletedAt(result.getString("deletedAt")).
                    build();
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public boolean existByPk( Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection,"SELECT * FROM customers WHERE id=?  AND deletedAt IS NULL", args[0]);
        return result.next();
    }

    @Override
    public boolean existByEmail( Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection,"SELECT * FROM customers WHERE email=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }

    @Override
    public boolean existByRegNo( Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection,"SELECT * FROM customers WHERE registrationNo=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }

    @Override
    public boolean existByNic( Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection,"SELECT * FROM customers WHERE nic=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }
}
