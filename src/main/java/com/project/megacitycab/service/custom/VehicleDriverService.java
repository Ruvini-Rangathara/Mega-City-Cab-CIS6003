package com.project.megacitycab.service.custom;

import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.dto.VehicleDriverDTO;
import com.project.megacitycab.service.CrudService;
import com.project.megacitycab.util.exception.MegaCityCabException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


public interface VehicleDriverService extends CrudService<VehicleDriverDTO> {
    boolean existsByVehicleId(Object... args) throws MegaCityCabException, SQLException;
    boolean existsByDriverId(Object... args) throws MegaCityCabException, SQLException;
    boolean existByLicensePlate(Object... args) throws MegaCityCabException, SQLException;
    boolean existsByDriverLicense(Object... args) throws MegaCityCabException, SQLException;
    List<VehicleDTO> getAllVehicles(Map<String, String> searchParams) throws MegaCityCabException, SQLException;
    DriverDTO findDriverByVehicleId(Object... args) throws MegaCityCabException, SQLException;
    VehicleDTO findVehicleByVehicleId(Object... args) throws MegaCityCabException, SQLException;
    DriverDTO findDriverByDriverId(Object... args) throws MegaCityCabException, SQLException;
    List<VehicleDriverDTO> getAvailableVehicles(LocalDate bookingDate, LocalTime pickupTime, LocalTime releaseTime) throws SQLException;
    List<DriverDTO> getAllDrivers(Map<String, String> searchParams) throws MegaCityCabException, SQLException;
    List<VehicleDriverDTO> getVehicleDriver() throws MegaCityCabException, SQLException;
}
