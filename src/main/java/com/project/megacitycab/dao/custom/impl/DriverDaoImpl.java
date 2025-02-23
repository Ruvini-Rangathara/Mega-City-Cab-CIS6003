package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.DriverDAO;
import com.project.megacitycab.dao.util.CrudUtil;
import com.project.megacitycab.entity.Driver;
import com.project.megacitycab.constant.DriverStatus;

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
        String sql = "INSERT INTO drivers (name, licenseNo, mobileNo, status, experience, email) VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(
                connection,
                sql,
                entity.getName(),
                entity.getLicenseNo(),
                entity.getMobileNo(),
                entity.getStatus().toString(), // Save status as string
                entity.getExperience(),
                entity.getEmail()
        );
    }

    @Override
    public boolean update(Connection connection, Driver entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE drivers SET name=?, licenseNo=?, mobileNo=?, status=?, experience=?, email=? WHERE id=?";
        return CrudUtil.execute(
                connection,
                sql,
                entity.getName(),
                entity.getLicenseNo(),
                entity.getMobileNo(),
                entity.getStatus().toString(),
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
            return new Driver.DriverBuilder()
                    .id(result.getString("id"))
                    .name(result.getString("name"))
                    .licenseNo(result.getString("licenseNumber"))
                    .mobileNo(result.getString("mobileNo"))
                    .status(DriverStatus.valueOf(result.getString("status")))
                    .experience(result.getInt("experience"))
                    .email(result.getString("email"))
                    .createdAt(result.getString("createdAt"))
                    .updatedAt(result.getString("updatedAt"))
                    .deletedAt(result.getString("deletedAt"))
                    .build();
        }
        return null;
    }

    @Override
    public List<Driver> getAll(Connection connection, Map<String, String> searchParams) throws SQLException, ClassNotFoundException {
        List<Driver> drivers = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM drivers WHERE deletedAt IS NULL");

        // Add search filters if provided
        if (searchParams != null) {
            if (searchParams.containsKey("name")) {
                sql.append(" AND name LIKE ?");
                params.add("%" + searchParams.get("name") + "%");
            }
            if (searchParams.containsKey("licenseNo")) {
                sql.append(" AND licenseNo LIKE ?");
                params.add("%" + searchParams.get("licenseNo") + "%");
            }
            if (searchParams.containsKey("mobileNo")) {
                sql.append(" AND mobileNo LIKE ?");
                params.add("%" + searchParams.get("mobileNo") + "%");
            }
            if (searchParams.containsKey("email")) {
                sql.append(" AND email LIKE ?");
                params.add("%" + searchParams.get("email") + "%");
            }
        }

        // Order by creation date descending
        sql.append(" ORDER BY createdAt DESC");

        ResultSet result = CrudUtil.execute(connection,sql.toString(), params.toArray());
        while (result.next()) {
            Driver driver = new Driver.DriverBuilder()
                    .id(result.getString("id"))
                    .name(result.getString("name"))
                    .licenseNo(result.getString("licenseNo"))
                    .mobileNo(result.getString("mobileNo"))
                    .status(DriverStatus.valueOf(result.getString("status")))
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
}
