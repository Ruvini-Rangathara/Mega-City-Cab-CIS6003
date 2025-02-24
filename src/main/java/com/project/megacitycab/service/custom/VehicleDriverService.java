package com.project.megacitycab.service.custom;

import com.project.megacitycab.dto.VehicleDriverDTO;
import com.project.megacitycab.service.CrudService;
import com.project.megacitycab.util.exception.MegaCityCabException;

import java.sql.SQLException;


public interface VehicleDriverService extends CrudService<VehicleDriverDTO> {
    boolean existsByVehicleId(Object... args) throws MegaCityCabException, SQLException;
    boolean existsByDriverId(Object... args) throws MegaCityCabException, SQLException;
    boolean existByLicensePlate(Object... args) throws MegaCityCabException, SQLException;
    boolean existsByDriverLicense(Object... args) throws MegaCityCabException, SQLException;
}
