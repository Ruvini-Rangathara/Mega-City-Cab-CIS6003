package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.entity.Driver;

import java.util.List;
import java.util.stream.Collectors;

public class DriverConverter {

    // Convert Driver (Entity) to DriverDTO (DTO)
    public static DriverDTO toDTO(Driver driver) {
        if (driver == null) {
            return null;
        }

        return new DriverDTO.DriverDTOBuilder()
                .id(driver.getId())
                .name(driver.getName())
                .licenseNo(driver.getLicenseNo())
                .mobileNo(driver.getMobileNo())
                .status(driver.getStatus())
                .experience(driver.getExperience())
                .email(driver.getEmail())
                .createdAt(driver.getCreatedAt())
                .updatedAt(driver.getUpdatedAt())
                .deletedAt(driver.getDeletedAt())
                .build();
    }

    // Convert DriverDTO (DTO) to Driver (Entity)
    public static Driver toEntity(DriverDTO driverDTO) {
        if (driverDTO == null) {
            return null;
        }

        return new Driver.DriverBuilder()
                .id(driverDTO.getId())
                .name(driverDTO.getName())
                .licenseNo(driverDTO.getLicenseNo())
                .mobileNo(driverDTO.getMobileNo())
                .status(driverDTO.getStatus())
                .experience(driverDTO.getExperience())
                .email(driverDTO.getEmail())
                .createdAt(driverDTO.getCreatedAt())
                .updatedAt(driverDTO.getUpdatedAt())
                .deletedAt(driverDTO.getDeletedAt())
                .build();
    }

    // Convert a list of Driver (Entity) to a list of DriverDTO (DTO)
    public static List<DriverDTO> toDTOList(List<Driver> drivers) {
        return drivers.stream().map(DriverConverter::toDTO).collect(Collectors.toList());
    }
}
