package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.entity.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

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
                .pricePerKm(vehicle.getPricePerKm())
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
                .pricePerKm(vehicleDTO.getPricePerKm())
                .status(vehicleDTO.getStatus())
                .createdAt(vehicleDTO.getCreatedAt())
                .updatedAt(vehicleDTO.getUpdatedAt())
                .deletedAt(vehicleDTO.getDeletedAt())
                .build();
    }

    // Convert a list of Vehicle (Entity) to a list of VehicleDTO (DTO)
    public static List<VehicleDTO> toDTOList(List<Vehicle> vehicles) {
        return vehicles.stream().map(VehicleConverter::toDTO).collect(Collectors.toList());
    }
}
