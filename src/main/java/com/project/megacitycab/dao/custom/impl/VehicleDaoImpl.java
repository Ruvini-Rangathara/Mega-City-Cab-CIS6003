package com.project.megacitycab.dao.custom.impl;

import com.project.megacitycab.dao.custom.VehicleDAO;
import com.project.megacitycab.dao.util.CrudUtil;
import com.project.megacitycab.entity.Vehicle;
import com.project.megacitycab.constant.VehicleStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VehicleDaoImpl implements VehicleDAO {

    public VehicleDaoImpl(){
    }

    @Override
    public boolean add(Connection connection, Vehicle entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(

                connection,"INSERT INTO vehicles (licensePlate, driverId, model, brand, capacity, color,pricePerKm,  status) VALUES (?,?,?,?,?,?,?,?)",
                entity.getLicensePlate(), entity.getDriverId(), entity.getModel(), entity.getBrand(),
                entity.getCapacity(), entity.getColor(),entity.getPricePerKm(), entity.getStatus().toString()
        );
    }

    @Override
    public boolean update(Connection connection, Vehicle entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                connection,"UPDATE vehicles SET licensePlate=?, driverId=?, model=?, brand=?, capacity=?, color=?, pricePerKm=?, status=? WHERE id=?",
                entity.getLicensePlate(), entity.getDriverId(), entity.getModel(), entity.getBrand(),
                entity.getCapacity(), entity.getColor(),entity.getPricePerKm(), entity.getStatus().toString(), entity.getId()
        );
    }

    @Override
    public boolean delete(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection,"UPDATE vehicles SET deletedAt=? WHERE id=?", new Date(), args[0]);
    }

    @Override
    public Vehicle searchById(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection,"SELECT * FROM vehicles WHERE id=? AND deletedAt IS NULL", args[0]);

        if (result.next()) {
            return new Vehicle.VehicleBuilder()
                    .id(result.getString("id"))
                    .licensePlate(result.getString("licensePlate"))
                    .driverId(result.getString("driverId"))
                    .model(result.getString("model"))
                    .brand(result.getString("brand"))
                    .capacity(result.getInt("capacity"))
                    .color(result.getString("color"))
                    .pricePerKm(result.getDouble("pricePerKm"))
                    .status(VehicleStatus.valueOf(result.getString("status")))
                    .createdAt(result.getString("createdAt"))
                    .updatedAt(result.getString("updatedAt"))
                    .deletedAt(result.getString("deletedAt"))
                    .build();
        }
        return null;
    }

    @Override
    public List<Vehicle> getAll(Connection connection, Map<String, String> searchParams) throws SQLException, ClassNotFoundException {
        List<Vehicle> vehicles = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM vehicles WHERE deletedAt IS NULL");

        if (searchParams != null) {
            if (searchParams.containsKey("licensePlate")) {
                sql.append(" AND licensePlate LIKE ?");
                params.add("%" + searchParams.get("licensePlate") + "%");
            }
            if (searchParams.containsKey("model")) {
                sql.append(" AND model LIKE ?");
                params.add("%" + searchParams.get("model") + "%");
            }
            if (searchParams.containsKey("brand")) {
                sql.append(" AND brand LIKE ?");
                params.add("%" + searchParams.get("brand") + "%");
            }
            if (searchParams.containsKey("status")) {
                sql.append(" AND status = ?");
                params.add(searchParams.get("status"));
            }
        }

        sql.append(" ORDER BY createdAt DESC");

        ResultSet result = CrudUtil.execute(connection,sql.toString(), params.toArray());

        while (result.next()) {
            Vehicle vehicle = new Vehicle.VehicleBuilder()
                    .id(result.getString("id"))
                    .licensePlate(result.getString("licensePlate"))
                    .driverId(result.getString("driverId"))
                    .model(result.getString("model"))
                    .brand(result.getString("brand"))
                    .capacity(result.getInt("capacity"))
                    .color(result.getString("color"))
                    .pricePerKm(result.getDouble("pricePerKm"))
                    .status(VehicleStatus.valueOf(result.getString("status")))
                    .createdAt(result.getString("createdAt"))
                    .updatedAt(result.getString("updatedAt"))
                    .deletedAt(result.getString("deletedAt"))
                    .build();
            vehicles.add(vehicle);
        }

        return vehicles;
    }

    @Override
    public boolean existByPk(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection,"SELECT * FROM vehicles WHERE id=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }

    @Override
    public boolean existByLicensePlate(Connection connection, Object... args) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection,"SELECT * FROM vehicles WHERE licensePlate=? AND deletedAt IS NULL", args[0]);
        return result.next();
    }
}
