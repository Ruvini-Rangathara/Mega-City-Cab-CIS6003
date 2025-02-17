package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.entity.Vehicle;

public class VehicleConverter {

    // Convert Vehicle (Entity) to VehicleDTO (DTO)
    public static VehicleDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        return new VehicleDTO.VehicleDTOBuilder()
                .id(vehicle.getId())
                .licensePlate(vehicle.getLicensePlate())
                .driverId(vehicle.getDriverId())
                .model(vehicle.getModel())
                .brand(vehicle.getBrand())
                .capacity(vehicle.getCapacity())
                .color(vehicle.getColor())
                .status(vehicle.getStatus())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .deletedAt(vehicle.getDeletedAt())
                .build();
    }

    // Convert VehicleDTO (DTO) to Vehicle (Entity)
    public static Vehicle toEntity(VehicleDTO vehicleDTO) {
        if (vehicleDTO == null) {
            return null;
        }

        return new Vehicle.VehicleBuilder()
                .id(vehicleDTO.getId())
                .licensePlate(vehicleDTO.getLicensePlate())
                .driverId(vehicleDTO.getDriverId())
                .model(vehicleDTO.getModel())
                .brand(vehicleDTO.getBrand())
                .capacity(vehicleDTO.getCapacity())
                .color(vehicleDTO.getColor())
                .status(vehicleDTO.getStatus())
                .createdAt(vehicleDTO.getCreatedAt())
                .updatedAt(vehicleDTO.getUpdatedAt())
                .deletedAt(vehicleDTO.getDeletedAt())
                .build();
    }
}
