package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.vehicle.VehicleDTO;
import com.project.megacitycab.entity.Vehicle;

public class VehicleConverter {

    // Convert Vehicle (Entity) to VehicleDTO (DTO)
    public static VehicleDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setLicensePlate(vehicle.getLicensePlate());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setBrand(vehicle.getBrand());
        vehicleDTO.setCapacity(vehicle.getCapacity());
        vehicleDTO.setColor(vehicle.getColor());
        vehicleDTO.setStatus(vehicle.getStatus());
        vehicleDTO.setCreatedAt(vehicle.getCreatedAt());
        vehicleDTO.setUpdatedAt(vehicle.getUpdatedAt());
        vehicleDTO.setDeletedAt(vehicle.getDeletedAt());

        return vehicleDTO;
    }

    // Convert VehicleDTO (DTO) to Vehicle (Entity)
    public static Vehicle toEntity(VehicleDTO vehicleDTO) {
        if (vehicleDTO == null) {
            return null;
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleDTO.getId());
        vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setBrand(vehicleDTO.getBrand());
        vehicle.setCapacity(vehicleDTO.getCapacity());
        vehicle.setColor(vehicleDTO.getColor());
        vehicle.setStatus(vehicleDTO.getStatus());
        vehicle.setCreatedAt(vehicleDTO.getCreatedAt());
        vehicle.setUpdatedAt(vehicleDTO.getUpdatedAt());
        vehicle.setDeletedAt(vehicleDTO.getDeletedAt());

        return vehicle;
    }
}
