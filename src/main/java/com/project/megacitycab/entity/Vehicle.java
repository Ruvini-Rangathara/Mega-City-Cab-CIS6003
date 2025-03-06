package com.project.megacitycab.entity;

import com.project.megacitycab.constant.VehicleStatus;

public class Vehicle implements SuperEntity {
    private String id;
    private String licensePlate;
    private String driverId;
    private String model;
    private String brand;
    private int capacity;
    private String color;
    private double pricePerKm;
    private VehicleStatus status;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    private Vehicle(VehicleBuilder builder) {
        this.id = builder.id;
        this.licensePlate = builder.licensePlate;
        this.driverId = builder.driverId;
        this.model = builder.model;
        this.brand = builder.brand;
        this.capacity = builder.capacity;
        this.color = builder.color;
        this.pricePerKm = builder.pricePerKm;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deletedAt = builder.deletedAt;
    }

    public static class VehicleBuilder {
        private String id;
        private String licensePlate;
        private String driverId;
        private String model;
        private String brand;
        private int capacity;
        private String color;
        private double pricePerKm;
        private VehicleStatus status;
        private String createdAt;
        private String updatedAt;
        private String deletedAt;

        public VehicleBuilder id(String id) {
            this.id = id;
            return this;
        }

        public VehicleBuilder licensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public VehicleBuilder driverId(String driverId) {
            this.driverId = driverId;
            return this;
        }

        public VehicleBuilder model(String model) {
            this.model = model;
            return this;
        }

        public VehicleBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public VehicleBuilder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public VehicleBuilder color(String color) {
            this.color = color;
            return this;
        }

        public VehicleBuilder pricePerKm(double pricePerKm) {
            this.pricePerKm = pricePerKm;
            return this;
        }

        public VehicleBuilder status(VehicleStatus status) {
            this.status = status;
            return this;
        }

        public VehicleBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public VehicleBuilder updatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public VehicleBuilder deletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
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
                ", pricePerKm=" + pricePerKm +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getColor() {
        return color;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }
}
