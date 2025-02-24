package com.project.megacitycab.service.custom;

import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.dto.VehicleDriverDTO;
import com.project.megacitycab.service.CrudService;
import com.project.megacitycab.util.exception.MegaCityCabException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface VehicleDriverService extends CrudService<VehicleDriverDTO> {
    boolean existsByVehicleId(Object... args) throws MegaCityCabException, SQLException;
    boolean existsByDriverId(Object... args) throws MegaCityCabException, SQLException;
    boolean existByLicensePlate(Object... args) throws MegaCityCabException, SQLException;
    boolean existsByDriverLicense(Object... args) throws MegaCityCabException, SQLException;
    List<VehicleDTO> getAllVehicles(Map<String, String> searchParams) throws MegaCityCabException, SQLException;
    DriverDTO findDriverByVehicleId(Object... args) throws MegaCityCabException, SQLException;
}
