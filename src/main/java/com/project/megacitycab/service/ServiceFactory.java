package com.project.megacitycab.service;

import com.project.megacitycab.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return serviceFactory == null ? serviceFactory = new ServiceFactory() : serviceFactory;
    }

    public <T extends SuperService>T getService(ServiceType type) {
        return switch (type) {
            case CUSTOMER_SERVICE_IMPL -> (T) new CustomerServiceImpl();
            case DRIVER_SERVICE_IMPL -> (T) new DriverServiceImpl();
            case BOOKING_SERVICE_IMPL -> (T) new BookingServiceImpl();
            case USER_SERVICE_IMPL -> (T) new UserServiceImpl();
            case VEHICLE_SERVICE_IMPL -> (T) new VehicleServiceImpl();
            default -> null;
        };
    }
}
