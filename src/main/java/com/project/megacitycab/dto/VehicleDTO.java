package com.project.megacitycab.dto;

import com.project.megacitycab.constant.VehicleStatus;

public class VehicleDTO implements SuperDTO {
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

    private VehicleDTO(VehicleDTOBuilder builder) {
        this.id = builder.id;
        this.licensePlate = builder.licensePlate;
        this.driverId = builder.driverId;
        this.model = builder.model;
        this.brand = builder.brand;
        this.capacity = builder.capacity;
        this.color = builder.color;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deletedAt = builder.deletedAt;
    }

    public static class VehicleDTOBuilder {
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

        public VehicleDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public VehicleDTOBuilder licensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public VehicleDTOBuilder driverId(String driverId) {
            this.driverId = driverId;
            return this;
        }

        public VehicleDTOBuilder model(String model) {
            this.model = model;
            return this;
        }

        public VehicleDTOBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public VehicleDTOBuilder capacity(double capacity) {
            this.capacity = capacity;
            return this;
        }

        public VehicleDTOBuilder color(String color) {
            this.color = color;
            return this;
        }

        public VehicleDTOBuilder status(VehicleStatus status) {
            this.status = status;
            return this;
        }

        public VehicleDTOBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public VehicleDTOBuilder updatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public VehicleDTOBuilder deletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public VehicleDTO build() {
            return new VehicleDTO(this);
        }
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
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

    public double getCapacity() {
        return capacity;
    }

    public String getColor() {
        return color;
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
