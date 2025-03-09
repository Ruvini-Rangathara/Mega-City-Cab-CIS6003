package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.DriverDAO;
import com.project.megacitycab.dao.util.CrudUtil;
import com.project.megacitycab.entity.Driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DriverDaoImpl implements DriverDAO {

    @Override
    public boolean add(Connection connection, Driver entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO drivers (name, licenseNo, mobileNo, experience, email) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(
                connection,
                sql,
                entity.getName(),
                entity.getLicenseNo(),
                entity.getMobileNo(),
                entity.getExperience(),
                entity.getEmail()
        );
    }

    @Override
    public boolean update(Connection connection, Driver entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE drivers SET name=?, licenseNo=?, mobileNo=?, experience=?, email=? WHERE id=?";
        return CrudUtil.execute(
                connection,
                sql,
                entity.getName(),
                entity.getLicenseNo(),
                entity.getMobileNo(),
                entity.getExperience(),
                entity.getEmail(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        // Soft delete by setting deletedAt timestamp
        String sql = "UPDATE drivers SET deletedAt=? WHERE id=?";
        return CrudUtil.execute(connection, sql, new Date(), args[0]);
    }

    @Override
    public Driver searchById(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM drivers WHERE id=? AND deletedAt IS NULL";
        ResultSet result = CrudUtil.execute(connection,sql, args[0]);
        if (result.next()) {
            Driver driver = new Driver.DriverBuilder()
                    .id(result.getString("id"))
                    .name(result.getString("name"))
                    .licenseNo(result.getString("licenseNo"))
                    .mobileNo(result.getString("mobileNo"))
                    .experience(result.getInt("experience"))
                    .email(result.getString("email"))
                    .createdAt(result.getString("createdAt"))
                    .updatedAt(result.getString("updatedAt"))
                    .deletedAt(result.getString("deletedAt"))
                    .build();
            return driver;
        }
        return null;
    }

    @Override
    public List<Driver> getAll(Connection connection, Map<String, String> searchParams) throws SQLException, ClassNotFoundException {
        List<Driver> drivers = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        // Base SQL query
        StringBuilder sql = new StringBuilder("SELECT * FROM drivers WHERE deletedAt IS NULL");

        // Add search filters only if searchParams is not null and contains meaningful values
        if (searchParams != null && !searchParams.isEmpty()) {
            if (searchParams.containsKey("name") && searchParams.get("name") != null && !searchParams.get("name").trim().isEmpty()) {
                sql.append(" AND name LIKE ?");
                params.add("%" + searchParams.get("name").trim() + "%");
            }
            if (searchParams.containsKey("licenseNo") && searchParams.get("licenseNo") != null && !searchParams.get("licenseNo").trim().isEmpty()) {
                sql.append(" AND licenseNo LIKE ?");
                params.add("%" + searchParams.get("licenseNo").trim() + "%");
            }
            if (searchParams.containsKey("mobileNo") && searchParams.get("mobileNo") != null && !searchParams.get("mobileNo").trim().isEmpty()) {
                sql.append(" AND mobileNo LIKE ?");
                params.add("%" + searchParams.get("mobileNo").trim() + "%");
            }
            if (searchParams.containsKey("email") && searchParams.get("email") != null && !searchParams.get("email").trim().isEmpty()) {
                sql.append(" AND email LIKE ?");
                params.add("%" + searchParams.get("email").trim() + "%");
            }
        }

        // Order by creation date descending
        sql.append(" ORDER BY createdAt DESC");

        // Execute query
        ResultSet result = CrudUtil.execute(connection, sql.toString(), params.toArray());
        while (result.next()) {
            Driver driver = new Driver.DriverBuilder()
                    .id(result.getString("id"))
                    .name(result.getString("name"))
                    .licenseNo(result.getString("licenseNo"))
                    .mobileNo(result.getString("mobileNo"))
                    .experience(result.getInt("experience"))
                    .email(result.getString("email"))
                    .createdAt(result.getString("createdAt"))
                    .updatedAt(result.getString("updatedAt"))
                    .deletedAt(result.getString("deletedAt"))
                    .build();
            drivers.add(driver);
        }

        return drivers;
    }
    @Override
    public boolean existByPk(Connection connection,Object... args) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM drivers WHERE id=? AND deletedAt IS NULL";
        ResultSet result = CrudUtil.execute(connection,sql, args[0]);
        return result.next();
    }

    @Override
    public boolean existByLicenseNo(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM drivers WHERE licenseNo=? AND deletedAt IS NULL";
        ResultSet result = CrudUtil.execute(connection,sql, args[0]);
        return result.next();
    }

    @Override
    public String getLastInsertedId(Connection connection) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM drivers ORDER BY createdAt DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(connection, sql);
        if (rs.next()) {
            return rs.getString(1);
        }
        throw new SQLException("Failed to get last inserted ID");
    }
}
