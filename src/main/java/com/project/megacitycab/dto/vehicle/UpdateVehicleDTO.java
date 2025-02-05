package com.project.megacitycab.dto.vehicle;

import com.project.megacitycab.constant.VehicleStatus;

public class UpdateVehicleDTO extends CreateVehicleDTO {
    private String id;

    public UpdateVehicleDTO() {
    }

    public UpdateVehicleDTO(String id, String licensePlate, String model, String brand, double capacity, String color, VehicleStatus status) {
        super(licensePlate, model, brand, capacity, color, status);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UpdateVehicleDTO{" +
                "id='" + id + '\'' +
                ", licensePlate='" + getLicensePlate() + '\'' +
                ", model='" + getModel() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", capacity='" + getCapacity() + '\'' +
                ", color='" + getColor() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
