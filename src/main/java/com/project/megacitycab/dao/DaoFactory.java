package com.project.megacitycab.dao;

import com.project.megacitycab.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return daoFactory == null ? daoFactory = new DaoFactory() : daoFactory;
    }

    public <T extends SuperDAO> T getDao(DaoTypes type) {
        return switch (type) {
            case CUSTOMER_DAO_IMPL -> (T) new CustomerDaoImpl();
            case DRIVER_DAO_IMPL -> (T) new DriverDaoImpl();
            case BOOKING_DAO_IMPL -> (T) new BookingDaoImpl();
            case VEHICLE_DAO_IMPL -> (T) new VehicleDaoImpl();
            case USER_DAO_IMPL -> (T) new UserDaoImpl();
            default -> null;
        };
    }
}
