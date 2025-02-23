package com.project.megacitycab.service.custom;

import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.dto.VehicleDriverDTO;
import com.project.megacitycab.service.CrudService;
import com.project.megacitycab.util.exception.MegaCityCabException;

import java.sql.Connection;


public interface VehicleDriverService extends CrudService<VehicleDriverDTO> {
    boolean existByLicensePlate(Object... args) throws MegaCityCabException;
    boolean existsByVehicleId(Object... args) throws MegaCityCabException;
    boolean existsByDriverId(Object... args) throws MegaCityCabException;
    boolean existsByDriverLicense(Object... args) throws MegaCityCabException;
}
