package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dao.DaoFactory;
import com.project.megacitycab.dao.DaoTypes;
import com.project.megacitycab.dao.custom.DriverDAO;
import com.project.megacitycab.dao.custom.VehicleDAO;
import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.dto.VehicleDriverDTO;
import com.project.megacitycab.service.custom.VehicleDriverService;
import com.project.megacitycab.util.DBUtil;
import com.project.megacitycab.util.converter.DriverConverter;
import com.project.megacitycab.util.converter.VehicleConverter;
import com.project.megacitycab.util.exception.MegaCityCabException;
import com.project.megacitycab.util.exception.MegaCityCabExceptionType;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleDriverServiceImpl implements VehicleDriverService {
    private static final Logger logger = Logger.getLogger(VehicleDriverServiceImpl.class.getName());
    private final VehicleDAO vehicleDAO;
    private final DriverDAO driverDAO;

    public VehicleDriverServiceImpl() {
        this.vehicleDAO = DaoFactory.getInstance().getDao(DaoTypes.VEHICLE_DAO_IMPL);
        this.driverDAO = DaoFactory.getInstance().getDao(DaoTypes.DRIVER_DAO_IMPL);
    }

    @Override
    public boolean add(VehicleDriverDTO vehicleDriverDTO) throws MegaCityCabException {
        Connection connection = null;
        try {
            if (!validateVehicleDriver(vehicleDriverDTO)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_VEHICLE_DRIVER_INPUTS);
            }

            connection = DBUtil.getConnection();

            // Check for existing license plate and driver license
            if (vehicleExistsByLicensePlate(connection, vehicleDriverDTO.getVehicle())) {
                throw new MegaCityCabException(MegaCityCabExceptionType.VEHICLE_ALREADY_EXISTS);
            }

            if (existsByDriverLicense(connection, vehicleDriverDTO.getDriver().getLicenseNo())) {
                throw new MegaCityCabException(MegaCityCabExceptionType.DRIVER_ALREADY_EXISTS);
            }

            // Prepare and save driver
            boolean driverSaved = driverDAO.add(connection, DriverConverter.toEntity(vehicleDriverDTO.getDriver()));

            // Prepare and save vehicle
            boolean vehicleSaved = vehicleDAO.add(connection, VehicleConverter.toEntity(vehicleDriverDTO.getVehicle()));

            if (!driverSaved || !vehicleSaved) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            DBUtil.rollback(connection);
            logger.log(Level.SEVERE, "Error in add vehicle-driver service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public boolean update(VehicleDriverDTO vehicleDriverDTO) throws MegaCityCabException {
        Connection connection = null;
        try {
            if (!validateVehicleDriver(vehicleDriverDTO)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INVALID_VEHICLE_DRIVER_INPUTS);
            }

            connection = DBUtil.getConnection();

            if (!existsByVehicleId(connection, vehicleDriverDTO.getVehicle().getId()) ||
                    !existsByDriverId(connection, vehicleDriverDTO.getDriver().getId())) {
                throw new MegaCityCabException(MegaCityCabExceptionType.VEHICLE_DRIVER_NOT_FOUND);
            }

            LocalDateTime timestamp = LocalDateTime.now();

            boolean driverUpdated = driverDAO.update(connection,
                    DriverConverter.toEntity(vehicleDriverDTO.getDriver()));
            boolean vehicleUpdated = vehicleDAO.update(connection,
                    VehicleConverter.toEntity(vehicleDriverDTO.getVehicle()));

            if (!driverUpdated || !vehicleUpdated) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            DBUtil.rollback(connection);
            logger.log(Level.SEVERE, "Error in update vehicle-driver service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public boolean delete(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();

            String vehicleId = (String) args[0];
            String driverId = (String) args[1];

            if (!existsByVehicleId(connection, vehicleId) || !existsByDriverId(connection, driverId)) {
                throw new MegaCityCabException(MegaCityCabExceptionType.VEHICLE_DRIVER_NOT_FOUND);
            }

            boolean vehicleDeleted = vehicleDAO.delete(connection, vehicleId);
            boolean driverDeleted = driverDAO.delete(connection, driverId);

            if (!vehicleDeleted || !driverDeleted) {
                throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            DBUtil.rollback(connection);
            logger.log(Level.SEVERE, "Error in delete vehicle-driver service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public VehicleDriverDTO searchById(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            VehicleDTO vehicle = VehicleConverter.toDTO(vehicleDAO.searchById(connection, args));

            if (vehicle == null) {
                return null;
            }

            DriverDTO driver = DriverConverter.toDTO(driverDAO.searchById(connection, vehicle.getDriverId()));
            return new VehicleDriverDTO.VehicleDriverDTOBuilder()
                    .driver(driver)
                    .vehicle(vehicle)
                    .build();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in search vehicle-driver by ID service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public List<VehicleDriverDTO> getAll(Map<String, String> searchParams) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();

            Map<String, String> cleanParams = new HashMap<>();
            if (searchParams != null) {
                searchParams.forEach((key, value) -> {
                    if (value != null && !value.trim().isEmpty()) {
                        cleanParams.put(key, value.trim());
                    }
                });
            }

            List<VehicleDriverDTO> result = new ArrayList<>();
            List<VehicleDTO> vehicles = VehicleConverter.toDTOList(vehicleDAO.getAll(connection, cleanParams));

            for (VehicleDTO vehicle : vehicles) {
                DriverDTO driver = DriverConverter.toDTO(driverDAO.searchById(connection, vehicle.getDriverId()));
                result.add(new VehicleDriverDTO.VehicleDriverDTOBuilder()
                        .driver(driver)
                        .vehicle(vehicle)
                        .build());
            }

            return result;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in get all vehicle-drivers service", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public boolean existByPk(Object... args) throws MegaCityCabException {
        return false;
    }

    // Helper methods
    private boolean validateVehicleDriver(VehicleDriverDTO vehicleDriver) {
        return validateDriver(vehicleDriver.getDriver()) && validateVehicle(vehicleDriver.getVehicle());
    }

    private boolean validateDriver(DriverDTO driver) {
        return driver != null &&
                driver.getLicenseNo() != null &&
                !driver.getLicenseNo().trim().isEmpty() &&
                driver.getName() != null &&
                !driver.getName().trim().isEmpty() &&
                driver.getMobileNo() != null &&
                driver.getMobileNo().matches("^(?:\\+94|0)7\\d{8}$") &&
                driver.getEmail() != null &&
                driver.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }

    private boolean validateVehicle(VehicleDTO vehicle) {
        return vehicle != null &&
                vehicle.getLicensePlate() != null &&
                !vehicle.getLicensePlate().trim().isEmpty() &&
                vehicle.getBrand() != null &&
                !vehicle.getBrand().trim().isEmpty() &&
                vehicle.getModel() != null &&
                !vehicle.getModel().trim().isEmpty();
    }

    private boolean vehicleExistsByLicensePlate(Connection connection, VehicleDTO vehicle) throws MegaCityCabException {
        try {
            return vehicleDAO.existByLicensePlate(connection, vehicle.getLicensePlate());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking vehicle exists by license plate", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existByLicensePlate(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            return vehicleDAO.existByLicensePlate(connection, args[0]);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking vehicle exists by license plate", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByVehicleId(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            return vehicleDAO.existByPk(connection, args[0]);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking vehicle exists by PK", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByDriverId(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            return driverDAO.existByPk(connection, args[0]);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking driver exists by PK", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByDriverLicense(Object... args) throws MegaCityCabException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            return driverDAO.existByLicenseNo(connection, args[0]);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking driver exists by license", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.INTERNAL_SERVER_ERROR);
        }
    }
}
