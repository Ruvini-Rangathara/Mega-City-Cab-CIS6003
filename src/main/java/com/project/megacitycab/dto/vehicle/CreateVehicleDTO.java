package com.project.megacitycab.dto.vehicle;

import com.project.megacitycab.constant.VehicleStatus;

public class CreateVehicleDTO {
    private String licensePlate;
    private String model;
    private String brand;
    private double capacity;
    private String color;
    private VehicleStatus status;

    public CreateVehicleDTO() {
    }

    public CreateVehicleDTO(String licensePlate, String model, String brand, double capacity, String color, VehicleStatus status) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.brand = brand;
        this.capacity = capacity;
        this.color = color;
        this.status = status;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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

    @Override
    public String toString() {
        return "CreateVehicleDTO{" +
                "licensePlate='" + licensePlate + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", capacity=" + capacity +
                ", color='" + color + '\'' +
                ", status=" + status +
                '}';
    }
}
