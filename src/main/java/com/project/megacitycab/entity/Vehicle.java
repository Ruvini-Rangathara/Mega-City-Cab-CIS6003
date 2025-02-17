package com.project.megacitycab.entity;

import com.project.megacitycab.constant.VehicleStatus;

public class Vehicle implements SuperEntity{
    private String id;
    private String licensePlate;
    private String driverId;
    private String model;
    private String brand;
    private double capacity;
    private String color;
    private VehicleStatus status;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public Vehicle() {
    }

    public Vehicle(String id, String licensePlate,String driverId, String model, String brand, double capacity, String color, VehicleStatus status, String createdAt, String updatedAt, String deletedAt) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.driverId = driverId;
        this.model = model;
        this.brand = brand;
        this.capacity = capacity;
        this.color = color;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public  String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }


    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", driverId='" + driverId + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", capacity=" + capacity +
                ", color='" + color + '\'' +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
