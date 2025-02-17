package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.entity.Driver;

public class DriverConverter {

    // Convert Driver (Entity) to DriverDTO (DTO)
    public static DriverDTO toDTO(Driver driver) {
        if (driver == null) {
            return null;
        }

        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(driver.getId());
        driverDTO.setName(driver.getName());
        driverDTO.setLicenseNo(driver.getLicenseNo());
        driverDTO.setMobileNo(driver.getMobileNo());
        driverDTO.setStatus(driver.getStatus());
        driverDTO.setExperience(driver.getExperience());
        driverDTO.setEmail(driver.getEmail());
        driverDTO.setCreatedAt(driver.getCreatedAt());
        driverDTO.setUpdatedAt(driver.getUpdatedAt());
        driverDTO.setDeletedAt(driver.getDeletedAt());

        return driverDTO;
    }

    // Convert DriverDTO (DTO) to Driver (Entity)
    public static Driver toEntity(DriverDTO driverDTO) {
        if (driverDTO == null) {
            return null;
        }

        Driver driver = new Driver();
        driver.setId(driverDTO.getId());
        driver.setName(driverDTO.getName());
        driver.setLicenseNo(driverDTO.getLicenseNo());
        driver.setMobileNo(driverDTO.getMobileNo());
        driver.setStatus(driverDTO.getStatus());
        driver.setExperience(driverDTO.getExperience());
        driver.setEmail(driverDTO.getEmail());
        driver.setCreatedAt(driverDTO.getCreatedAt());
        driver.setUpdatedAt(driverDTO.getUpdatedAt());
        driver.setDeletedAt(driverDTO.getDeletedAt());

        return driver;
    }
}
