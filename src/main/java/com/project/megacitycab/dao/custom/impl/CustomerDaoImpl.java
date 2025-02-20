package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.CustomerDAO;
import com.project.megacitycab.dao.util.CrudUtil;
import com.project.megacitycab.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDaoImpl implements CustomerDAO {
    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO customers (registrationNo, name, address, nic, dob, mobileNo, email) VALUES (?,?,?,?,?,?,?)", entity.getRegistrationNo(), entity.getName(), entity.getAddress(), entity.getNic(), entity.getDob(), entity.getMobileNo(), entity.getEmail());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE customers SET name=?, address=?, nic=?, dob=?, mobileNo=?, email=? WHERE id=?", entity.getName(), entity.getAddress(), entity.getNic(), entity.getDob(), entity.getMobileNo(), entity.getEmail(), entity.getId());
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE customers SET deletedAt=? WHERE id=?", new Date(), args[0]);
    }

    @Override
    public Customer searchById(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM customers WHERE id=?  AND deletedAt IS NULL", args[0]);

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
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM customers WHERE deletedAt IS NULL");
        List<Customer> list = new ArrayList<>();

        while (result.next()) {
            Customer customer = new Customer.CustomerBuilder()
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

            list.add(customer);
        }

        return list;
    }


    @Override
    public boolean existByPk(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM customers WHERE id=?  AND deletedAt IS NULL", args[0]);
        return result.next();
    }


    @Override
    public boolean existByEmail(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM customers WHERE email=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }

    @Override
    public boolean existByRegNo(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM customers WHERE registrationNo=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }

    @Override
    public boolean existByNic(Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM customers WHERE nic=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }
}
